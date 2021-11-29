package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.GradleMetadata
import com.github.lamba92.npmtomaven.models.MavenPom
import com.github.lamba92.npmtomaven.models.NpmMetadata
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.response.*
import io.ktor.util.cio.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.Logger
import java.io.File
import kotlin.coroutines.coroutineContext

suspend fun generateGradleMetadata(
    json: Json,
    packageName: String,
    packageVersion: String,
    fileKey: String,
    file: Deferred<File>,
    client: HttpClient,
    objectCache: ObjectCache,
    npmMetadata: NpmMetadata,
    sha512: Deferred<String>,
    sha256: Deferred<String>,
    sha1: Deferred<String>,
    md5: Deferred<String>
): String {
    val fileSize = objectCache.getOrPutObject("$fileKey/size") {
        file.await().length()
    }

    return json.encodeToString(
        GradleMetadata(
            component = GradleMetadata.Component(
                group = "npm",
                module = packageName,
                version = packageVersion,
                attributes = mapOf("org.gradle.status" to "release")
            ),
            createdBy = GradleMetadata.CreatedBy(
                gradle = GradleMetadata.CreatedBy.Gradle("7.3")
            ),
            variants = listOf(
                GradleMetadata.Variant(
                    name = "npmPackage",
                    attributes = mapOf(
                        "org.gradle.libraryelements" to "npm-tarball",
                        "org.gradle.usage" to "js"
                    ),
                    dependencies = npmMetadata.dependencies.parallelMapNotNull { (dependencyName, dependencyVersion) ->
                        val normalizedVersion = normalizeNpmPackageVersion(
                            dependencyName = dependencyName,
                            dependencyVersion = dependencyVersion,
                            client = client
                        )
                        if (normalizedVersion != null)
                            GradleMetadata.Variant.Dependency(
                                group = "npm",
                                module = dependencyName,
                                version = GradleMetadata.Variant.Dependency.Version(
                                    requires = normalizedVersion
                                )
                            )
                        else null
                    },
                    files = listOf(
                        GradleMetadata.Variant.File(
                            name = "$packageName-$packageVersion.tgz",
                            url = "$packageName-$packageVersion.tgz",
                            sha512 = sha512.await(),
                            sha256 = sha256.await(),
                            sha1 = sha1.await(),
                            md5 = md5.await(),
                            size = fileSize
                        )
                    )
                )
            )
        )
    )
}

suspend fun <K, V, R> Map<K, V>.parallelMapNotNull(parallelism: Int = 10, transform: suspend (Map.Entry<K, V>) -> R?) =
    withContext(coroutineContext + coroutineContext[CoroutineDispatcher.Key]!!.limitedParallelism(parallelism)) {
        map { async { transform(it) } }.awaitAll().filterNotNull()
    }


suspend fun tarballFromCache(
    fileCache: FileCache,
    fileKey: String,
    client: HttpClient,
    npmMetadata: NpmMetadata
) = fileCache.getOrPutBytes(fileKey) { client.get<HttpResponse>(npmMetadata.dist.tarball).content.toByteArray() }

@Suppress("BlockingMethodInNonBlockingContext", "SuspendFunctionOnCoroutineScope")
suspend fun hash(
    objectKey: String,
    objectCache: ObjectCache,
    file: Deferred<File>,
    digest: DigestUtils,
): String = objectCache.getOrPutObject(objectKey) {
    withContext(Dispatchers.IO) {
        digest.digestAsHex(file.await())
    }
}

suspend fun generatePom(
    xml: XML,
    packageName: String,
    packageVersion: String,
    npmMetadata: NpmMetadata,
    addMetadataAnnotation: Boolean,
    client: HttpClient,
    logger: Logger
): String {
    val message = xml.encodeToString(
        MavenPom(
            groupId = "npm",
            artifactId = packageName,
            version = packageVersion,
            packaging = "npm",
            developers = npmMetadata.author?.let { listOf(it) } ?: emptyList(),
            dependencies = npmMetadata.dependencies.mapNotNull { (dependencyName, dependencyVersion) ->
                val normalizedVersion = normalizeNpmPackageVersion(dependencyName, dependencyVersion, client)
                if (normalizedVersion != null)
                    MavenPom.Dependency(
                        groupId = "npm",
                        artifactId = dependencyName,
                        version = normalizedVersion,
                        scope = "runtime"
                    )
                else {
                    logger.warn("Dependency $packageName/$packageVersion failed to be normalized")
                    null
                }
            }
        )
    )
    return if (addMetadataAnnotation) message.insertAtLine(2, GRADLE_METADATA_POM_ANNOTATION) else message
}

suspend fun ApplicationCall.redirectTarball(tarballUrl: String) =
    respondRedirect(tarballUrl)
