package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmMetadata
import com.github.yuchi.semver.Version
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import org.slf4j.event.Level
import java.nio.file.Path
import java.nio.file.Paths
import io.ktor.client.engine.cio.CIO as CIOClient

fun main() {
    setupNpmToMavenRepository().start(true)
}

fun setupNpmToMavenRepository(
    port: Int = 8080,
    host: String = "0.0.0.0",
    cacheDir: Path = Paths.get("./build/npm-caches")
): ApplicationEngine {

    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val xml = XML {
        indentString = "  "
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Charset
    }

    val client = HttpClient(CIOClient) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    val security = Security()

    val fileCache: FileCache = LocalFileCache(cacheDir.resolve("files"))

    val objectCache: ObjectCache = FileBasedObjectCache(cacheDir.resolve("objects"), json)

    return embeddedServer(CIO, port = port, host = host) {
        install(CallLogging) {
            level = Level.TRACE
            filter { true }
        }
        install(NotFoundInterceptor) {
            notFoundAction {

                val urlSegments = call.request.path()
                    .removePrefix("/")
                    .removePrefix("npm/")
                    .split("/")

                val indexOfVersion = urlSegments.indexOfFirst {
                    runCatching { Version.from(it, true) }.getOrNull() != null
                }.takeIf { it >= 0 } ?: error("Unable to parse version for $urlSegments")

                val packageName = urlSegments.take(indexOfVersion).joinToString("/")
                val packageVersion = Version.from(urlSegments[indexOfVersion], true)
                val artifact = urlSegments.subList(indexOfVersion + 1, urlSegments.size).joinToString("/")

                val generateGradleMetadata = call.request.queryParameters["gradleMetadata"]?.toBoolean() ?: true

                val key = "$packageName/$packageVersion"

                val npmMetadata = objectCache.getOrPutObject("$key.npmmetadata.json") {
                    client.get<NpmMetadata>("https://registry.npmjs.org/$key")
                }

                val tarballKey = "$packageName/$packageVersion.tgz"

                val tarballFile = async(start = CoroutineStart.LAZY) {
                    tarballFromCache(fileCache, tarballKey, client, npmMetadata)
                }

                val tarballSha512: Deferred<String> = async {
                    hash("$tarballKey/sha512", objectCache, tarballFile, security.sha512)
                }
                val tarballSha256: Deferred<String> = async {
                    hash("$tarballKey/sha256", objectCache, tarballFile, security.sha256)
                }
                val tarballSha1: Deferred<String> = async {
                    hash("$tarballKey/sha1", objectCache, tarballFile, security.sha1)
                }
                val tarballMd5: Deferred<String> = async {
                    hash("$tarballKey/md5", objectCache, tarballFile, security.md5)
                }

                when {
                    artifact.endsWith(".pom") -> call.respondText(ContentType.Application.Xml) {
                        getOrCachePom(
                            objectCache,
                            key,
                            xml,
                            packageName,
                            packageVersion,
                            npmMetadata,
                            generateGradleMetadata,
                            client,
                            log
                        )
                    }
                    artifact.endsWith(".tgz") -> call.redirectTarball(npmMetadata.dist.tarball)
                    artifact.endsWith(".module") -> call.respondText(ContentType.Application.Json) {
                        getOrCacheModule(
                            objectCache,
                            key,
                            json,
                            packageName,
                            packageVersion,
                            tarballKey,
                            tarballFile,
                            client,
                            npmMetadata,
                            tarballSha512,
                            tarballSha256,
                            tarballSha1,
                            tarballMd5
                        )
                    }
                    artifact.endsWith(".tgz.sha512") -> call.respondText(tarballSha512.await())
                    artifact.endsWith(".tgz.sha256") -> call.respondText(tarballSha256.await())
                    artifact.endsWith(".tgz.sha1") -> call.respondText(tarballSha1.await())
                    artifact.endsWith(".tgz.md5") -> call.respondText(tarballMd5.await())
                    artifact.endsWith(".pom.sha1") -> call.respondPomSha1(
                        objectCache = objectCache,
                        key = key,
                        security = security,
                        xml = xml,
                        packageName = packageName,
                        packageVersion = packageVersion,
                        npmMetadata = npmMetadata,
                        generateGradleMetadata = generateGradleMetadata,
                        client = client
                    )
                    artifact.endsWith(".module.sha1") -> respondGradleMetadataSha1(
                        objectCache = objectCache,
                        key = key,
                        security = security,
                        json = json,
                        packageName = packageName,
                        packageVersion = packageVersion,
                        tarballKey = tarballKey,
                        tarballFile = tarballFile,
                        client = client,
                        npmMetadata = npmMetadata,
                        tarballSha512 = tarballSha512,
                        tarballSha256 = tarballSha256,
                        tarballSha1 = tarballSha1,
                        tarballMd5 = tarballMd5
                    )
                }
            }
        }
    }
}


