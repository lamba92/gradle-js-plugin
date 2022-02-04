plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

allprojects {

    group = "com.github.lamba92"
    version = "0.0.1"

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        jcenter()
    }
}

kotlin {
    target {
        sourceSets {
            all {
                languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                languageSettings.optIn("kotlin.RequiresOptIn")
                languageSettings.optIn("kotlin.time.ExperimentalTime")
                languageSettings.optIn("kotlin.ExperimentalStdlibApi")
            }
        }
    }
}

application {
    mainClass.set("com.github.lamba92.npmtomaven.ApplicationKt")
}

dependencies {

    implementation("org.jetbrains.exposed:exposed-jdbc:0.36.1")
    val ktorVersion = "1.6.7"

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-tomcat:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    val coroutinesVersion = "1.6.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")

    val kodeinDbVersion = "0.8.1-beta"

    implementation("org.kodein.db:kodein-db:$kodeinDbVersion")
    implementation("org.kodein.db:kodein-leveldb-jni-jvm-windows:$kodeinDbVersion")

    implementation("io.github.pdvrieze.xmlutil:serialization:0.84.0")
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("com.github.yuchi:npm-semver:1.0.0")
    implementation("commons-codec:commons-codec:1.15")

    testImplementation(kotlin("test-junit5"))

    val junitVersion = "5.8.2"

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    implementation("org.xerial:sqlite-jdbc:3.36.0.2")

}

val testCaches = file("$buildDir/tests/caches")
val db = file("$buildDir/tests/db.sqlite")

tasks {
    val deleteTestCaches by registering(Delete::class) {
        delete(testCaches)
    }

    withType<Test> {
        dependsOn(deleteTestCaches)
        useJUnitPlatform()
        environment("NPM_OBJECT_CACHE", testCaches.resolve("objects"))
        environment("NPM_FILE_CACHE", testCaches.resolve("files"))
        environment("DB_PATH", db.absolutePath)
    }
}