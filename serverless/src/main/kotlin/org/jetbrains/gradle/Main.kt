package org.jetbrains.gradle

import externals.aws.APIGatewayEvent
import externals.aws.APIGatewayProxyResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.json.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.jetbrains.gradle.data.NpmArtifactCoordinates
import org.jetbrains.gradle.data.NpmVersionedPackageMetadata
import parse

val json
    get() = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

val client
    get() = HttpClient(Js) {
        install(JsonPlugin) {
            serializer = KotlinxSerializer(json)
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

val defaultManager
    get() = NpmArtifactManager(
        json = json,
        caches = NoopDatasource,
        client = client,
        security = Security(),
    )

suspend fun APIGatewayEvent.respond(manager: NpmArtifactManager = defaultManager): APIGatewayProxyResult {
    val urlSegments = path.removePrefix("/")
        .removePrefix("npm/")
        .split("/")

    val indexOfVersion = urlSegments.indexOfFirst {
        runCatching { parse(it) }.getOrNull() != null
    }.takeIf { it >= 0 } ?: error("Unable to parse version for $urlSegments")

    val packageName = urlSegments.take(indexOfVersion).joinToString("/")
    val packageVersion = runCatching { parse(urlSegments[indexOfVersion], true) }.getOrNull()
        ?: error("Unable to parse version ${urlSegments[indexOfVersion]}")
    val artifact = urlSegments.subList(indexOfVersion + 1, urlSegments.size).joinToString("/")
    val coordinates = NpmArtifactCoordinates(packageName, packageVersion)

    return when {
        artifact.endsWith(".tgz.sha512") -> APIGatewayProxyTextResult(manager.resultFor(coordinates).hashes.tarballSha512)
        artifact.endsWith(".tgz.sha256") -> APIGatewayProxyTextResult(manager.resultFor(coordinates).hashes.tarballSha256)
        artifact.endsWith(".tgz.sha1") -> APIGatewayProxyTextResult(manager.resultFor(coordinates).hashes.tarballSha1)
        artifact.endsWith(".tgz.md5") -> APIGatewayProxyTextResult(manager.resultFor(coordinates).hashes.tarballMd5)
        artifact.endsWith(".pom.sha1") -> APIGatewayProxyTextResult(manager.resultFor(coordinates).hashes.pomSha1)
        artifact.endsWith(".module.sha1") -> APIGatewayProxyTextResult(manager.resultFor(coordinates).hashes.moduleSha1)
        artifact.endsWith(".tgz") -> retry {
            client.get("https://registry.npmjs.org/${coordinates.key}").body<NpmVersionedPackageMetadata>()
        }.dist.tarball.let { APIGatewayProxyRedirectResult(it) }
        artifact.endsWith(".pom") -> APIGatewayProxyResult(
            manager.resultFor(coordinates).pom,
            ContentType.Application.Xml
        )
        artifact.endsWith(".module") -> APIGatewayProxyResult(
            manager.resultFor(coordinates).module,
            ContentType.Application.Json
        )
        else -> APIGatewayProxyResult(HttpStatusCode.NotFound)
    }
}

