package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.GradleMetadata
import com.github.lamba92.npmtomaven.models.MavenPom
import com.github.lamba92.npmtomaven.models.NpmMetadata
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ApplicationCall.respondGradleMetadata(
    json: Json,
    packageName: String,
    packageVersion: String,
    client: HttpClient,
    security: Security,
    fileCache: FileCache,
    objectCache: ObjectCache,
    npmMetadata: NpmMetadata
): Unit = coroutineScope {
    val fileKey = "$packageName/$packageVersion.tgz"

    val file = async(start = CoroutineStart.LAZY) {
        fileCache.getFile(fileKey)
            ?: withContext(Dispatchers.IO) { kotlin.io.path.createTempFile().toFile() }
                .also { client.get<HttpResponse>(npmMetadata.dist.tarball).content.copyTo(it.writeChannel()) }
                .also { application.launch { fileCache.saveFile(it, fileKey) } }
    }

    val sha512: Deferred<String> = async {
        val objectKey = "$fileKey/sha512"
        objectCache.getObject(objectKey)
            ?: withContext(Dispatchers.IO) { security.sha512.digestAsHex(file.await()) }
                .also { application.launch { objectCache.saveObject(it, objectKey) } }
    }
    val sha256: Deferred<String> = async {
        val objectKey = "$fileKey/sha256"
        objectCache.getObject("$fileKey/sha256")
            ?: withContext(Dispatchers.IO) { security.sha256.digestAsHex(file.await()) }
                .also { application.launch { objectCache.saveObject(it, objectKey) } }
    }
    val sha1: Deferred<String> = async {
        val objectKey = "$fileKey/sha1"
        objectCache.getObject("$fileKey/sha1")
            ?: withContext(Dispatchers.IO) { security.sha1.digestAsHex(file.await()) }
                .also { application.launch { objectCache.saveObject(it, objectKey) } }
    }
    val md5: Deferred<String> = async {
        val objectKey = "$fileKey/md5"
        objectCache.getObject("$fileKey/md5")
            ?: withContext(Dispatchers.IO) { security.md5.digestAsHex(file.await()) }
                .also { application.launch { objectCache.saveObject(it, objectKey) } }
    }
    val fileSize: Deferred<Long> = async {
        val objectKey = "$fileKey/size"
        objectCache.getObject("$fileKey/size")
            ?: withContext(Dispatchers.IO) { file.await().length() }
                .also { application.launch { objectCache.saveObject(it, objectKey) } }
    }

    respondText(
        json.encodeToString(
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
                        dependencies = npmMetadata.dependencies.parallelMap(10) { (dependencyName, dependencyVersion) ->
                            GradleMetadata.Variant.Dependency(
                                group = "npm",
                                module = dependencyName,
                                version = GradleMetadata.Variant.Dependency.Version(
                                    requires = normalizeNpmPackageVersion(
                                        dependencyName = dependencyName,
                                        dependencyVersion = dependencyVersion,
                                        client = client
                                    )
                                )
                            )
                        },
                        files = listOf(
                            GradleMetadata.Variant.File(
                                name = "$packageName-$packageVersion.tgz",
                                url = "$packageName-$packageVersion.tgz",
                                sha512 = sha512.await(),
                                sha256 = sha256.await(),
                                sha1 = sha1.await(),
                                md5 = md5.await(),
                                size = fileSize.await()
                            )
                        )
                    )
                )
            )
        ),
        ContentType.Application.Json
    )
}

suspend fun ApplicationCall.respondPom(
    xml: XML,
    packageName: String,
    packageVersion: String,
    npmMetadata: NpmMetadata,
    addMetadataAnnotation: Boolean,
    client: HttpClient
) {
    val message = xml.encodeToString(
        MavenPom(
            groupId = "npm",
            artifactId = packageName,
            version = packageVersion,
            packaging = "npm",
            developers = npmMetadata.author?.let { listOf(it) } ?: emptyList(),
            dependencies = npmMetadata.dependencies.parallelMap(10) { (dependencyName, dependencyVersion) ->
                MavenPom.Dependency(
                    groupId = "npm",
                    artifactId = dependencyName,
                    version = normalizeNpmPackageVersion(dependencyName, dependencyVersion, client),
                    scope = "runtime"
                )
            }
        )
    )
    respondText(
        if (addMetadataAnnotation) message.insertAtLine(2, GRADLE_METADATA_POM_ANNOTATION) else message,
        ContentType.Application.Xml
    )
}

suspend fun ApplicationCall.redirectTarball(tarballUrl: String) =
    respondRedirect(tarballUrl)