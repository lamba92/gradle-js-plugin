package org.jetbrains.gradle

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.json.serializer.*
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.json.Json

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
