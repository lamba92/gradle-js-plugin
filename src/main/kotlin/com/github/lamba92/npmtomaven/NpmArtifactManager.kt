package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmPackageMetadata
import com.github.lamba92.npmtomaven.models.NpmVersionedPackageMetadata
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import org.slf4j.LoggerFactory
import kotlin.io.path.createTempFile
import kotlin.io.path.fileSize
import kotlin.io.path.outputStream
import kotlin.io.path.writeText

class NpmArtifactManager(
    private val xml: XML,
    private val json: Json,
    private val caches: CacheDatasource,
    private val client: HttpClient,
    private val security: Security
) {

    private val logger = LoggerFactory.getLogger(NpmArtifactManager::class.java)

    suspend fun getPomFor(coordinates: NpmArtifactCoordinates) = generatePom(
        xml = xml,
        packageName = coordinates.name,
        packageVersion = coordinates.version.toString(),
        npmVersionedPackageMetadata = retry { client.get("https://registry.npmjs.org/${coordinates.key}") },
        publicationDate = retry { client.get<NpmPackageMetadata>("https://registry.npmjs.org/${coordinates.name}") }
            .time[coordinates.version.toString()]
            ?: error("Unable to find publication date for ${coordinates.key}"),
        client = client,
        logger = logger,
    )

    data class HashesResult(val hashes: NpmArtifactHashes, val pom: String? = null, val module: String? = null)

    private suspend fun getHashesResultFor(coordinates: NpmArtifactCoordinates): HashesResult {
        val cacheHashes = caches.getArtifactDataFor(coordinates)
        if (cacheHashes != null) return HashesResult(cacheHashes)

        val coordinatesMetadata =
            retry { client.get<NpmVersionedPackageMetadata>("https://registry.npmjs.org/${coordinates.key}") }
        val tarball = retry {
            val file = createTempFile()
            val download = client.get<HttpResponse>(coordinatesMetadata.dist.tarball).content.toInputStream()
            download.use { intput -> file.outputStream().use { output -> intput.transferTo(output) } }
            file
        }

        val pom = getPomFor(coordinates)
        val pomFile = createTempFile().also { it.writeText(pom) }

        val tarballSha512 = security.sha512.digestAsHex(tarball)
        val tarballSha256 = security.sha256.digestAsHex(tarball)
        val tarballSha1 = security.sha1.digestAsHex(tarball)
        val tarballMd5 = security.md5.digestAsHex(tarball)
        val pomSha1 = security.sha1.digestAsHex(pomFile)
        val tarballFileSize = tarball.fileSize()

        val packageMetadata = retry { client.get<NpmPackageMetadata>("https://registry.npmjs.org/${coordinates.name}") }

        val publicationDate = packageMetadata.time[coordinates.version.toString()] ?: error("Unable to find publication date for ${coordinates.key}")

        val module = generateGradleMetadata(
            json,
            coordinates.name,
            coordinates.version.toString(),
            client,
            tarball.fileSize(),
            coordinatesMetadata,
            publicationDate,
            tarballSha512,
            tarballSha256,
            tarballSha1,
            tarballMd5
        )
        val moduleFile = createTempFile().also { it.writeText(module) }

        val hashes = NpmArtifactHashes(
            coordinates,
            tarballSha512,
            tarballSha256,
            tarballSha1,
            tarballMd5,
            pomSha1,
            security.sha1.digestAsHex(moduleFile),
            tarballFileSize
        )

        caches.saveArtifactData(hashes)

        return HashesResult(hashes, pom, module)
    }

    suspend fun getHashesFor(coordinates: NpmArtifactCoordinates) = getHashesResultFor(coordinates).hashes

    suspend fun getGradleMetadataFor(coordinates: NpmArtifactCoordinates): String {
        val (hashes, _, module) = getHashesResultFor(coordinates)

        return module ?: generateGradleMetadata(
            json,
            coordinates.name,
            coordinates.version.toString(),
            client,
            hashes.tarballSize,
            retry { client.get("https://registry.npmjs.org/${coordinates.key}") },
            retry { client.get<NpmPackageMetadata>("https://registry.npmjs.org/${coordinates.name}") }
                .time[coordinates.version.toString()]
                ?: error("Unable to find publication date for ${coordinates.key}"),
            hashes.tarballSha512,
            hashes.tarballSha256,
            hashes.tarballSha1,
            hashes.tarballMd5
        )
    }

}