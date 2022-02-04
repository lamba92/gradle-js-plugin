package com.github.lamba92.npmtomaven.tests

import com.github.lamba92.npmtomaven.*
import com.github.lamba92.npmtomaven.models.GradleMetadata
import com.github.lamba92.npmtomaven.models.MavenPom
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import org.jetbrains.exposed.sql.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.slf4j.event.Level
import java.sql.Connection


@ExperimentalCoroutinesApi
@FlowPreview
class ResolutionTests {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    val db = Database.connect("jdbc:sqlite:${System.getenv("DB_PATH")?: "db.sqlite"}")

    val caches = SQLCacheDatasource(db, Connection.TRANSACTION_SERIALIZABLE)

    private val xml = XML {
        indentString = "  "
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Charset
    }

    private val httpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    val manager = NpmArtifactManager(xml, json, caches, httpClient, Security())


    fun withNpmToMavenTestApplication(test: suspend TestApplicationEngine.() -> Unit): Unit = withTestApplication({
        install(CallLogging) {
            level = Level.INFO
            filter { true }
        }
        NpmToGradle(manager, httpClient)
    }) {
        runBlocking { test() }
    }

    @ParameterizedTest(name = "Resolve POM for {0}")
    @ArgumentsSource(TestPackages::class)
    fun resolvePomRecursively(npmCoordinates: NpmCoordinates) =
        withNpmToMavenTestApplication {
            val coordinatesQueue = Channel<NpmCoordinates>(UNLIMITED)
            coordinatesQueue.send(npmCoordinates)
            var jobsCount = 0
            val mutex = Mutex()
            for (coordinate in coordinatesQueue) {
                launch(Dispatchers.Default.limitedParallelism(2)) {
                    mutex.withLock { jobsCount++ }
                    getPomFor(coordinate).dependencies.elements.map { NpmCoordinates(it.artifactId, it.version) }
                        .forEach { coordinatesQueue.send(it) }
                    mutex.withLock {
                        jobsCount--
                        if (jobsCount == 0 && coordinatesQueue.isEmpty) coordinatesQueue.close()
                    }
                }
            }
        }

    @ParameterizedTest(name = "Resolve Gradle Metadata for {0}")
    @ArgumentsSource(TestPackages::class)
    fun resolveModuleRecursively(npmCoordinates: NpmCoordinates) =
        withNpmToMavenTestApplication {
            val coordinatesQueue = Channel<NpmCoordinates>(UNLIMITED)
            coordinatesQueue.send(npmCoordinates)
            var jobsCount = 0
            val mutex = Mutex()
            for (coordinate in coordinatesQueue) {
                launch {
                    mutex.withLock { jobsCount++ }
                    getModuleFor(coordinate).variants.single { it.name == "npmPackage" }.dependencies
                        .map { NpmCoordinates(it.module, it.version.requires) }
                        .forEach { coordinatesQueue.send(it) }
                    mutex.withLock {
                        jobsCount--
                        if (jobsCount == 0 && coordinatesQueue.isEmpty) coordinatesQueue.close()
                    }
                }
            }
        }

    private fun TestApplicationEngine.getPomFor(npmCoordinates: NpmCoordinates): MavenPom =
        with(
            handleRequest(
                HttpMethod.Get, "/npm/${npmCoordinates.name}/${npmCoordinates.version}/" +
                        "${npmCoordinates.name}-${npmCoordinates.version}.pom"
            )
        ) {
            xml.decodeFromString<MavenPom>(response.content ?: error("Body for ${request.uri} was null"))
                .also { println("Solved dependencies for $npmCoordinates") }
        }

    private fun TestApplicationEngine.getModuleFor(npmCoordinates: NpmCoordinates): GradleMetadata =
        with(
            handleRequest(
                HttpMethod.Get, "/npm/${npmCoordinates.name}/${npmCoordinates.version}/" +
                        "${npmCoordinates.name}-${npmCoordinates.version}.module"
            )
        ) {
            println("Solved dependencies for $npmCoordinates")
            json.decodeFromString(response.content ?: error("Body for ${request.uri} was null"))
        }
}