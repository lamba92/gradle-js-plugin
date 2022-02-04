import GenerateWebpackConfig.TerserPluginSettings
import com.github.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnSetupTask

plugins {
    kotlin("js")
    kotlin("plugin.serialization")
    id("com.github.node-gradle.node") version "3.1.1"
}

kotlin {
    js(IR) {
        useCommonJs()
        nodejs {
            binaries.executable()
        }
    }
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
    val ktorVersion = "2.0.0-beta-1"

    api(npm("semver", "7.3.5"))
    api(npm("@types/semver", "7.3.9"))
    api(npm("abort-controller", "3.0.0"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    api("io.ktor:ktor-client-js:$ktorVersion")
    api("io.ktor:ktor-client-serialization:$ktorVersion")
    api("io.ktor:ktor-client-logging:$ktorVersion")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    api("io.github.pdvrieze.xmlutil:serialization:0.84.0")
    api("io.github.microutils:kotlin-logging:2.1.21")
    api("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
}

val kotlinNodeJsSetup by rootProject.tasks.getting(NodeJsSetupTask::class)
val kotlinYarnSetup by rootProject.tasks.getting(YarnSetupTask::class)
val kotlinNpmInstall by rootProject.tasks.getting(KotlinNpmInstallTask::class)

tasks {

    val compileProductionExecutableKotlinJs by getting(KotlinJsIrLink::class)
    val outputProductionBundle = file("$buildDir/bundles/${project.name}-production-bundled.js")

    val generateProdWebpackConfig by registering(GenerateWebpackConfig::class) {
        dependsOn(compileProductionExecutableKotlinJs)
        mode = GenerateWebpackConfig.Mode.PRODUCTION
        entryFile = compileProductionExecutableKotlinJs.outputFileProperty.get()
        target = GenerateWebpackConfig.Target.NODE
        modulesFolder.set(listOf(kotlinNpmInstall.nodeModulesDir))
        outputBundleName = outputProductionBundle.name
        outputBundleFolder = outputProductionBundle.parent
        outputConfig = file("$buildDir/webpack/webpack.prod.js")
        terserSettings.set(
            TerserPluginSettings(
                parallel = true,
                terserOptions = TerserPluginSettings.Options(
                    mangle = true,
                    sourceMaps = true,
                    keepClassnames = Regex("AbortSignal"),
                    keepFileNames = Regex("AbortSignal")
                )
            )
        )
    }

    register<NodeTask>("createProductionJsBundle") {
        dependsOn(compileProductionExecutableKotlinJs, kotlinNpmInstall, generateProdWebpackConfig)
        outputs.file(outputProductionBundle)
        script.set(kotlinNpmInstall.nodeModulesDir.resolve("webpack-cli/bin/cli.js"))
        environment.put("NODE_PATH", kotlinNpmInstall.nodeModulesDir.absolutePath)
        args.set(listOf("-c", generateProdWebpackConfig.get().outputConfig.absolutePath))
        inputs.file(generateProdWebpackConfig.get().outputConfig)
        outputs.file(generateProdWebpackConfig.get().outputBundleFile)
    }
}