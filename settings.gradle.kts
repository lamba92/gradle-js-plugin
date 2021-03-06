enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    plugins {
        val kotlinVersion = "1.6.10"
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("com.github.node-gradle.node") version "3.1.1"
    }
}

rootProject.name = "npm-to-maven-server"

include(":gradleNpmTest", ":aws", ":serverless", ":serverless:tests")
