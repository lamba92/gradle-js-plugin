package org.jetbrains.gradle

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.gradle.data.*

class NpmArtifactManager(
    private val json: Json,
    private val caches: CacheDatasource,
    private val client: HttpClient,
    private val security: Security,
) {
    data class Result(val hashes: NpmArtifactHashes, val pom: String, val module: String)

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun resultFor(coordinates: NpmArtifactCoordinates): Result {
        val cacheHashes = caches.getArtifactDataFor(coordinates)
        if (cacheHashes != null) return cacheHashes

        val coordinatesMetadata: NpmVersionedPackageMetadata =
            retry { client.get("https://registry.npmjs.org/${coordinates.key}").body() }

        val packageMetadata: NpmPackageMetadata =
            retry { client.get("https://registry.npmjs.org/${coordinates.name}").body() }

        val publicationDate = packageMetadata.time[coordinates.version.toString()]
            ?: error("Unable to find publication date for ${coordinates.key}")

        val tarball = client.get(coordinatesMetadata.dist.tarball).bodyAsChannel().toByteArray()

        val normalizedDependencies = coordinatesMetadata.dependencies
            .parallelMapNotNull { (name, version) ->
                normalizeNpmPackageVersion(name, version, publicationDate, client)?.let { name to it }
            }.toMap()

        val pom = getPomFor(coordinates, coordinatesMetadata, normalizedDependencies)

        val tarballSha512 = security.sha512.digestAsHex(tarball)
        val tarballSha256 = security.sha256.digestAsHex(tarball)
        val tarballSha1 = security.sha1.digestAsHex(tarball)
        val tarballMd5 = security.md5.digestAsHex(tarball)
        val pomSha1 = security.sha1.digestAsHex(pom)

        val module: String = generateGradleMetadata(
            json = json,
            packageName = coordinates.name,
            packageVersion = coordinates.version.toString(),
            fileSize = tarball.size.toLong(),
            normalizedDependencies = normalizedDependencies,
            sha512 = tarballSha512,
            sha256 = tarballSha256,
            sha1 = tarballSha1,
            md5 = tarballMd5
        )

        val hashes = NpmArtifactHashes(
            coordinates = coordinates,
            tarballSha512 = tarballSha512,
            tarballSha256 = tarballSha256,
            tarballSha1 = tarballSha1,
            tarballMd5 = tarballMd5,
            pomSha1 = pomSha1,
            moduleSha1 = security.sha1.digestAsHex(module),
            tarballSize = tarball.size.toLong()
        )

        val result = Result(hashes, pom, module)

        GlobalScope.launch { caches.saveArtifactData(result) }

        return result
    }

    private fun getPomFor(
        coordinates: NpmArtifactCoordinates,
        npmVersionedPackageMetadata: NpmVersionedPackageMetadata,
        normalizedDependencies: Map<String, String>
    ) = MavenPom(
        groupId = "npm",
        artifactId = coordinates.name,
        version = coordinates.version.toString(),
        packaging = "npm",
        developers = npmVersionedPackageMetadata.author?.let { listOf(it) } ?: emptyList(),
        dependencies = normalizedDependencies.map { (name, version) ->
            MavenPom.Dependency("npm", name, version, "npm")
        }
    ).toXml().insertAtLine(2, GRADLE_METADATA_POM_ANNOTATION)

    private fun generateGradleMetadata(
        json: Json,
        packageName: String,
        packageVersion: String,
        fileSize: Long,
        normalizedDependencies: Map<String, String>,
        sha512: String,
        sha256: String,
        sha1: String,
        md5: String
    ) = json.encodeToString(
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
                    dependencies = normalizedDependencies.map { (name, version) ->
                        GradleMetadata.Variant.Dependency(
                            group = "npm",
                            module = name,
                            version = GradleMetadata.Variant.Dependency.Version(
                                requires = version
                            )
                        )
                    },
                    files = listOf(
                        GradleMetadata.Variant.File(
                            name = "$packageName-$packageVersion.tgz",
                            url = "$packageName-$packageVersion.tgz",
                            sha512 = sha512,
                            sha256 = sha256,
                            sha1 = sha1,
                            md5 = md5,
                            size = fileSize
                        )
                    )
                )
            )
        )
    )

}

