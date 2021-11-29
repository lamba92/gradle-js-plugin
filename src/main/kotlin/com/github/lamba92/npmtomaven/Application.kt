package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmMetadata
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.launch
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
    }

    val xml = XML {
        indentString = "  "
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Charset
    }

    val client = HttpClient(CIOClient) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    val security = Security()

    val fileCache: FileCache = LocalFileCache(cacheDir.resolve("files"))

    val objectCache: ObjectCache = FileBasedObjectCache(cacheDir.resolve("objects"), json)

    return embeddedServer(CIO, port = port, host = host) {
        install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/") }
        }
        install(NotFoundInterceptor) {
            notFoundAction {
                val (packageName, packageVersion, artifact) =
                    call.request.path().split("/").takeLast(3)

                val generateGradleMetadata = call.request.queryParameters["gradleMetadata"]?.toBoolean() ?: true

                val key = "$packageName/$packageVersion"

                val npmMetadata = objectCache.getObject("$key.npmmetadata.json")
                    ?: client.get<NpmMetadata>("https://registry.npmjs.org/$packageName/$packageVersion")
                        .also { launch { objectCache.saveObject(it, "$key.npmmetadata.json") } }

                when {
                    artifact.endsWith(".pom") -> call.respondPom(
                        xml = xml,
                        packageName = packageName,
                        packageVersion = packageVersion,
                        npmMetadata = npmMetadata,
                        addMetadataAnnotation = generateGradleMetadata,
                        client = client
                    )
                    artifact.endsWith(".tgz") -> call.redirectTarball(npmMetadata.dist.tarball)
                    artifact.endsWith(".module") -> call.respondGradleMetadata(
                        json = json,
                        packageName = packageName,
                        packageVersion = packageVersion,
                        npmMetadata = npmMetadata,
                        client = client,
                        security = security,
                        fileCache = fileCache,
                        objectCache = objectCache
                    )
                }
            }
        }
    }
}

