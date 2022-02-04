package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmVersionedPackageMetadata
import com.github.lamba92.npmtomaven.sql.NpmPackageHashesTable
import com.github.lamba92.npmtomaven.sql.NpmPackageHashesTable.packageName
import com.github.lamba92.npmtomaven.sql.NpmPackageHashesTable.packageVersion
import com.github.yuchi.semver.Version
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
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
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

data class NpmArtifactCoordinates(val name: String, val version: Version) {

    val key
        get() = "$name/$version"

    override fun toString() = "$name@$version"
}

data class NpmArtifactHashes(
    val coordinates: NpmArtifactCoordinates,
    val tarballSha512: String,
    val tarballSha256: String,
    val tarballSha1: String,
    val tarballMd5: String,
    val pomSha1: String,
    val moduleSha1: String,
    val tarballSize: Long
)

interface CacheDatasource {
    suspend fun getArtifactDataFor(coordinates: NpmArtifactCoordinates): NpmArtifactHashes?
    suspend fun saveArtifactData(data: NpmArtifactHashes)
}

class SQLCacheDatasource(private val database: Database, private val transactionIsolation: Int) : CacheDatasource {
    override suspend fun getArtifactDataFor(coordinates: NpmArtifactCoordinates): NpmArtifactHashes? =
        newSuspendedTransaction(db = database, transactionIsolation = transactionIsolation) {
            val resultRow = NpmPackageHashesTable.select {
                (packageName eq coordinates.name) and (packageVersion eq coordinates.version.toString())
            }.singleOrNull() ?: return@newSuspendedTransaction null

            NpmArtifactHashes(
                coordinates,
                resultRow[NpmPackageHashesTable.tarballSha512],
                resultRow[NpmPackageHashesTable.tarballSha256],
                resultRow[NpmPackageHashesTable.tarballSha1],
                resultRow[NpmPackageHashesTable.tarballMd5],
                resultRow[NpmPackageHashesTable.pomSha1],
                resultRow[NpmPackageHashesTable.moduleSha1],
                resultRow[NpmPackageHashesTable.tarballSize]
            )
        }

    override suspend fun saveArtifactData(data: NpmArtifactHashes) =
        newSuspendedTransaction(db = database, transactionIsolation = transactionIsolation) {
            val hasHash = NpmPackageHashesTable.select {
                (packageName eq data.coordinates.name) and (packageVersion eq data.coordinates.version.toString())
            }.singleOrNull() != null
            if (!hasHash) {
                NpmPackageHashesTable.insert {
                    it[packageName] = data.coordinates.name
                    it[packageVersion] = data.coordinates.version.toString()
                    it[tarballSha512] = data.tarballSha512
                    it[tarballSha256] = data.tarballSha256
                    it[tarballSha1] = data.tarballSha1
                    it[tarballMd5] = data.tarballMd5
                    it[pomSha1] = data.pomSha1
                    it[moduleSha1] = data.moduleSha1
                    it[tarballSize] = data.tarballSize
                }
            }
        }

}

fun Application.NpmToGradle(manager: NpmArtifactManager, client: HttpClient) {
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

            val coordinates = NpmArtifactCoordinates(packageName, packageVersion)

            when {
                artifact.endsWith(".tgz.sha512") -> call.respondText(manager.getHashesFor(coordinates).tarballSha512)
                artifact.endsWith(".tgz.sha256") -> call.respondText(manager.getHashesFor(coordinates).tarballSha256)
                artifact.endsWith(".tgz.sha1") -> call.respondText(manager.getHashesFor(coordinates).tarballSha1)
                artifact.endsWith(".tgz.md5") -> call.respondText(manager.getHashesFor(coordinates).tarballMd5)
                artifact.endsWith(".pom.sha1") -> call.respondText(manager.getHashesFor(coordinates).pomSha1)
                artifact.endsWith(".module.sha1") -> call.respondText(manager.getHashesFor(coordinates).moduleSha1)
                artifact.endsWith(".tgz") -> call.respondRedirect(
                    retry { client.retrieveNpmVersionedPackageMetadata(coordinates) }.dist.tarball
                )
                artifact.endsWith(".pom") -> call.respondText(
                    manager.getPomFor(coordinates),
                    ContentType.Application.Xml
                )
                artifact.endsWith(".module") -> call.respondText(
                    manager.getGradleMetadataFor(coordinates),
                    ContentType.Application.Json
                )
            }
        }
    }
}

suspend fun HttpClient.retrieveNpmVersionedPackageMetadata(
    coordinates: NpmArtifactCoordinates
) = get<NpmVersionedPackageMetadata>("https://registry.npmjs.org/${coordinates.key}")

fun main() {

    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val db = Database.connect("jdbc:sqlite:${System.getenv("DB_PATH") ?: "db.sqlite"}")

    val caches = SQLCacheDatasource(db, Connection.TRANSACTION_SERIALIZABLE)

    val xml = XML {
        indentString = "  "
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Charset
    }

    val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    val manager = NpmArtifactManager(xml, json, caches, httpClient, Security())

    transaction(transactionIsolation = Connection.TRANSACTION_SERIALIZABLE, db = db, repetitionAttempts = 3) {
        SchemaUtils.createMissingTablesAndColumns(NpmPackageHashesTable)
        NpmPackageHashesTable.deleteAll()
    }

    embeddedServer(Tomcat, port = 8080) {
        NpmToGradle(manager, httpClient)
    }.start(true)
}