package org.jetbrains.gradle

import externals.aws.APIGatewayEvent
import externals.aws.APIGatewayProxyResult
import externals.semver.parse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.jetbrains.gradle.data.NpmArtifactCoordinates
import org.jetbrains.gradle.data.NpmVersionedPackageMetadata

suspend fun APIGatewayEvent.respond(manager: NpmArtifactManager = defaultManager): APIGatewayProxyResult {
    val urlSegments = path.removePrefix("/")
        .removePrefix("npm/")
        .split("/")

    val indexOfVersion = urlSegments.dropLast(1)
        .indexOfFirst { runCatching { parse(it) }.getOrNull() != null }
        .takeIf { it >= 0 }
        ?: error("Unable to parse version for $urlSegments")

    val packageName = urlSegments.take(indexOfVersion).joinToString("/")
    val packageVersion = runCatching { parse(urlSegments[indexOfVersion], true) }.getOrNull()
        ?: error("Unable to externals.semver.parse version ${urlSegments[indexOfVersion]}")
    val artifact = urlSegments.subList(indexOfVersion + 1, urlSegments.size).joinToString("/")
    val coordinates = NpmArtifactCoordinates(packageName, packageVersion)

    return when {
        artifact.endsWith(".tgz.sha512") -> APIGatewayProxyTextResult(manager.getHashes(coordinates).tarballSha512)
        artifact.endsWith(".tgz.sha256") -> APIGatewayProxyTextResult(manager.getHashes(coordinates).tarballSha256)
        artifact.endsWith(".tgz.sha1") -> APIGatewayProxyTextResult(manager.getHashes(coordinates).tarballSha1)
        artifact.endsWith(".tgz.md5") -> APIGatewayProxyTextResult(manager.getHashes(coordinates).tarballMd5)
        artifact.endsWith(".pom.sha1") -> APIGatewayProxyTextResult(manager.getHashes(coordinates).pomSha1)
        artifact.endsWith(".module.sha1") -> APIGatewayProxyTextResult(manager.getHashes(coordinates).moduleSha1)
        artifact.endsWith(".tgz") -> retry {
            client.get("https://registry.npmjs.org/${coordinates.npmArtifactName}").body<NpmVersionedPackageMetadata>()
        }.dist.tarball.let { APIGatewayProxyRedirectResult(it) }
        artifact.endsWith(".pom") -> APIGatewayProxyResult(
            manager.getPom(coordinates),
            ContentType.Application.Xml
        )
        artifact.endsWith(".module") -> APIGatewayProxyResult(
            manager.getGradleMetadata(coordinates),
            ContentType.Application.Json
        )
        else -> APIGatewayProxyResult(HttpStatusCode.NotFound)
    }
}
