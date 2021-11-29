plugins {

    val kotlinVersion = "1.6.0"

    application
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

group = "lamba92.github.com"
version = "0.0.1"

kotlin {
    target {
        sourceSets {
            all {
                languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }
}

application {
    mainClass.set("com.github.lamba92.npmtomaven.ApplicationKt")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

dependencies {

    val ktorVersion = "1.6.4"

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:1.6.4")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")

    val kodeinDbVersion = "0.8.1-beta"

    implementation("org.kodein.db:kodein-db:$kodeinDbVersion")
    implementation("org.kodein.db:kodein-leveldb-jni-jvm-windows:$kodeinDbVersion")

    implementation("io.github.pdvrieze.xmlutil:serialization:0.84.0-RC1")
    implementation("ch.qos.logback:logback-classic:1.2.7")
    implementation("net.swiftzer.semver:semver:1.1.2")
    implementation("commons-codec:commons-codec:1.15")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation(kotlin("test-junit5"))
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}