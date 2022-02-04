@file:Suppress("FunctionName")

package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmPackageMetadata
import com.github.lamba92.npmtomaven.models.NpmVersionedPackageMetadata
import com.github.yuchi.semver.Version
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    setupNpmToMavenRepository().start(true)
}

fun setupNpmToMavenRepository(
    port: Int = 8080,
    host: String = "0.0.0.0",
    cacheDir: Path = Paths.get("./build/npm-caches")
): ApplicationEngine {

    val fileCache: FileCache = LocalFileCache(cacheDir.resolve("files"))

    val objectCache: ObjectCache = FileBasedObjectCache(
        path = cacheDir.resolve("objects"),
        json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    )

    return embeddedServer(Tomcat, port = port, host = host) {
        NpmToMavenApplicationServer(objectCache, fileCache)
    }
}

fun Application.NpmToMavenApplicationServer(
    objectCache: ObjectCache,
    fileCache: FileCache,
    json: Json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    },
    client: HttpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    },
    security: Security = Security(),
    xml: XML = XML {
        indentString = "  "
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Charset
    }
) {
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
                ?: error("Unable to parse version ${urlSegments[indexOfVersion]}")
            val artifact = urlSegments.subList(indexOfVersion + 1, urlSegments.size).joinToString("/")

            val generateGradleMetadata = call.request.queryParameters["gradleMetadata"]?.toBoolean() ?: true

            val key = "$packageName/$packageVersion"

            val npmPackageMetadataPublicationDate = suspend {
                objectCache.getOrPutObject("$packageName.npmmetadata.json") {
                    retry { client.get<NpmPackageMetadata>("https://registry.npmjs.org/$packageName") }
                }.time[packageVersion.toString()] ?: error("Unable to get publication date for $key")
            }

            val npmVersionedPackageMetadata = objectCache.getOrPutObject("$key.npmmetadata.json") {
                retry { client.get<NpmVersionedPackageMetadata>("https://registry.npmjs.org/$key") }
            }

            val tarballKey = "$packageName/$packageVersion.tgz"

            val tarballFile = suspend {
                tarballFromCache(fileCache, tarballKey, client, npmVersionedPackageMetadata)
            }

            val tarballSha512 = suspend {
                hash("$tarballKey/sha512", objectCache, tarballFile, security.sha512)
            }
            val tarballSha256 = suspend {
                hash("$tarballKey/sha256", objectCache, tarballFile, security.sha256)
            }
            val tarballSha1 = suspend {
                hash("$tarballKey/sha1", objectCache, tarballFile, security.sha1)
            }
            val tarballMd5 = suspend {
                hash("$tarballKey/md5", objectCache, tarballFile, security.md5)
            }

            when {
                artifact.endsWith(".pom") -> call.respondText(ContentType.Application.Xml) {
                    getOrCachePom(
                        objectCache = objectCache,
                        key = key,
                        xml = xml,
                        packageName = packageName,
                        packageVersion = packageVersion,
                        npmVersionedPackageMetadata = npmVersionedPackageMetadata,
                        publicationDate = npmPackageMetadataPublicationDate(),
                        client = client,
                        logger = log
                    )
                }
                artifact.endsWith(".tgz") -> call.redirectTarball(npmVersionedPackageMetadata.dist.tarball)
                artifact.endsWith(".module") -> call.respondText(ContentType.Application.Json) {
                    getOrCacheModule(
                        objectCache = objectCache,
                        key = key,
                        json = json,
                        packageName = packageName,
                        packageVersion = packageVersion,
                        tarballKey = tarballKey,
                        tarballFile = tarballFile,
                        client = client,
                        npmMetadata = npmVersionedPackageMetadata,
                        publicationDate = npmPackageMetadataPublicationDate(),
                        tarballSha512 = tarballSha512,
                        tarballSha256 = tarballSha256,
                        tarballSha1 = tarballSha1,
                        tarballMd5 = tarballMd5
                    )
                }
                artifact.endsWith(".tgz.sha512") -> call.respondText(tarballSha512())
                artifact.endsWith(".tgz.sha256") -> call.respondText(tarballSha256())
                artifact.endsWith(".tgz.sha1") -> call.respondText(tarballSha1())
                artifact.endsWith(".tgz.md5") -> call.respondText(tarballMd5())
                artifact.endsWith(".pom.sha1") -> call.respondPomSha1(
                    objectCache = objectCache,
                    key = key,
                    security = security,
                    xml = xml,
                    packageName = packageName,
                    packageVersion = packageVersion,
                    npmMetadata = npmVersionedPackageMetadata,
                    publicationDate = npmPackageMetadataPublicationDate(),
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
                    npmMetadata = npmVersionedPackageMetadata,
                    publicationDate = npmPackageMetadataPublicationDate(),
                    tarballSha512 = tarballSha512,
                    tarballSha256 = tarballSha256,
                    tarballSha1 = tarballSha1,
                    tarballMd5 = tarballMd5
                )
            }
        }
    }
}