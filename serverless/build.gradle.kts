import com.github.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    kotlin("js")
    kotlin("plugin.serialization")
    id("com.github.node-gradle.node")
}

node {
    version.set("17.4.0")
    download.set(true)
}

kotlin {
    js(IR) {
        compilations.all {
            kotlinOptions {
            }
        }
        useCommonJs()
        nodejs {
            binaries.executable()
        }
    }
    sourceSets {
        all {
            listOf(
                "kotlinx.coroutines.DelicateCoroutinesApi",
                "kotlin.js.ExperimentalJsExport",
                "kotlinx.serialization.ExperimentalSerializationApi"
            ).forEach { languageSettings.optIn(it) }
        }
    }
}
dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
    val ktorVersion = "2.0.0-beta-1"

    api(npm("semver", "7.3.5"))
    api(npm("abort-controller", "3.0.0"))
    api(npm("bufferutil", "4.0.6"))
    api(npm("utf-8-validate", "5.0.8"))
    api(npm("encoding", "0.1.13"))

    val coroutinesVersion = "1.6.0"

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("io.ktor:ktor-client-js:$ktorVersion")
    api("io.ktor:ktor-client-serialization:$ktorVersion")
    api("io.ktor:ktor-client-logging:$ktorVersion")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    api("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")

    testImplementation(kotlin("test"))
    testImplementation(npm("webpack-cli", "4.9.2"))
    testImplementation(npm("webpack", "5.68.0"))
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}

val kotlinNpmInstall by rootProject.tasks.getting(KotlinNpmInstallTask::class)

tasks {

    val compileProductionExecutableKotlinJs by getting(KotlinJsIrLink::class)

    val patchedKotlinJsOutput = file("$buildDir/outputs/${project.name}-patched.js")
    val outputProductionBundle = file("$buildDir/bundles/${project.name}-production-bundled.js")
    val generatedWebpackConfig = file("$buildDir/webpack/webpack.prod.js")

    val patchKotlinJsOutput by registering(Copy::class) {
        dependsOn(compileProductionExecutableKotlinJs)
        from(compileProductionExecutableKotlinJs.outputFileProperty) {
            filter { it.replace("eval('require')", "require") }
            rename { patchedKotlinJsOutput.name }
        }
        into(patchedKotlinJsOutput.parent)
    }

    val generateProdWebpackConfig by registering(Sync::class) {
        dependsOn(patchKotlinJsOutput)
        from(projectDir.resolve("src/main/webpack/webpack.prod.js")) {
            filter { line ->
                line.replace("%ENTRY_POINT%", patchedKotlinJsOutput.absolutePath)
                    .replace("%OUTPUT_DIR_PATH%", outputProductionBundle.parent)
                    .replace("%OUTPUT_FILE_NAME%", outputProductionBundle.name)
                    .replace("%NODE_MODULES_DIR%", kotlinNpmInstall.nodeModulesDir.absolutePath)
            }
            rename { generatedWebpackConfig.name }
        }

        into(generatedWebpackConfig.parent)
    }

    val createProductionJsBundle by registering(NodeTask::class) {
        dependsOn(compileProductionExecutableKotlinJs, kotlinNpmInstall, generateProdWebpackConfig)
        script.set(kotlinNpmInstall.nodeModulesDir.resolve("webpack-cli/bin/cli.js"))
        environment.put("NODE_PATH", kotlinNpmInstall.nodeModulesDir.absolutePath)
        args.set(listOf("-c", generatedWebpackConfig.absolutePath))
        inputs.file(generatedWebpackConfig)
        outputs.file(outputProductionBundle)
    }

    register<NodeTask>("runJsTest") {
        dependsOn(createProductionJsBundle)
        script.set(file("src/test/js/test.js"))
        args.set(listOf(outputProductionBundle.absolutePath))
    }

    withType<Test> {
        testLogging.showStandardStreams = true
    }
}
