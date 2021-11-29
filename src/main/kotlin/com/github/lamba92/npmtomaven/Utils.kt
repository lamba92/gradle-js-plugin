package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmPackageInfo
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import net.swiftzer.semver.SemVer

fun String.insertAtLine(index: Int, line: String) =
    lines().toMutableList().apply { add(index, line) }.joinToString("\n")

val GRADLE_METADATA_POM_ANNOTATION = """
  <!-- This module was also published with a richer model, Gradle metadata,  -->
  <!-- which should be used instead. Do not delete the following line which  -->
  <!-- is to indicate to Gradle or any Gradle module metadata file consumer  -->
  <!-- that they should prefer consuming it instead. -->
  <!-- do_not_remove: published-with-gradle-metadata -->
""".trimMargin()

suspend fun <K, V, T> Map<K, V>.parallelMap(
    parallelism: Int? = null,
    function: suspend (Map.Entry<K, V>) -> T
): List<T> =
    withContext(if (parallelism != null) Dispatchers.Default.limitedParallelism(parallelism) else Dispatchers.Default) {
        map { async { function(it) } }.awaitAll()
    }

suspend fun normalizeNpmPackageVersion(
    dependencyName: String,
    dependencyVersion: String,
    client: HttpClient
) = coroutineScope {
    val versions = async(start = CoroutineStart.LAZY) {
        // do not cache, it may change over time. Stupid open ranges...
        client.get<NpmPackageInfo>("https://registry.npmjs.org/$dependencyName").versions
    }
    val semVer by lazy { SemVer.parse(dependencyVersion.removePrefix("~").removePrefix("^")) }
    when {
        dependencyVersion.startsWith("~") -> {
            val toString = versions.await().keys.asSequence().map { SemVer.parse(it) }
                .filter { it.major == semVer.major }.maxByOrNull { it.minor }?.toString()
            toString ?: dependencyVersion
        }
        dependencyVersion.startsWith("^") -> {
            val toString = versions.await().keys.asSequence().map { SemVer.parse(it) }
                .filter { it.major == semVer.major && it.minor == semVer.minor }.maxByOrNull { it.patch }?.toString()
            toString ?: dependencyVersion
        }
        else -> dependencyVersion
    }
}