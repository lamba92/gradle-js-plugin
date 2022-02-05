import GenerateWebpackConfig.TerserPluginSettings
import com.github.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnSetupTask

plugins {
    kotlin("js")
    id("com.github.node-gradle.node")
}

kotlin {
    js(IR) {
        useCommonJs()
        compilations.all {
            kotlinOptions {
                moduleKind = "amd"
            }
        }
        nodejs {
            binaries.executable()
        }
    }
}

dependencies {
    implementation(project(":serverless"))
    api(npm("abort-controller", "3.0.0"))
    api("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
    testImplementation(npm("webpack-cli", "4.9.2"))
    testImplementation(npm("webpack", "5.68.0"))
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
                parallel = false,
                terserOptions = TerserPluginSettings.Options(
                    mangle = false,
                    sourceMaps = false,
                    keepClassnames = Regex("AbortSignal|abort\\-controller|AbortController"),
                    keepFileNames = Regex("AbortSignal|abort\\-controller|AbortController")
                )
            )
        )
    }

    val createProductionJsBundle by registering(NodeTask::class) {
        dependsOn(compileProductionExecutableKotlinJs, kotlinNpmInstall, generateProdWebpackConfig)
        outputs.file(outputProductionBundle)
        script.set(kotlinNpmInstall.nodeModulesDir.resolve("webpack-cli/bin/cli.js"))
        environment.put("NODE_PATH", kotlinNpmInstall.nodeModulesDir.absolutePath)
        args.set(listOf("-c", generateProdWebpackConfig.get().outputConfig.absolutePath))
        inputs.file(generateProdWebpackConfig.get().outputConfig)
        outputs.file(generateProdWebpackConfig.get().outputBundleFile)
    }

    register<NodeTask>("runProductionJsBundle") {
        dependsOn(createProductionJsBundle)
        script.set(outputProductionBundle)
    }

    register<NodeTask>("runMain") {
        dependsOn(compileProductionExecutableKotlinJs)
        environment.put("NODE_PATH", kotlinNpmInstall.nodeModulesDir.absolutePath)
        script.set(outputProductionBundle)
    }
}