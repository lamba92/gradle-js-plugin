package org.jetbrains.gradle

import Range
import SemVer
import crypto.Hash
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.datetime.Instant
import org.jetbrains.gradle.data.NpmPackageMetadata
import org.khronos.webgl.Uint8Array
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


fun <K, V> Map<K, V>.toJsObject(): dynamic {
    val obj = js("{}")
    forEach { (k, v) -> obj[k] = v }
    return obj
}

fun <K, V> Map<K, List<V>>.toJsObject(): dynamic {
    val obj = js("{}")
    forEach { (k, v) -> obj[k] = v.toTypedArray() }
    return obj
}typealias Callback = (error: Throwable?, result: Any) -> Unit

fun String.insertAtLine(index: Int, line: String) =
    lines().toMutableList().apply { add(index, line) }.joinToString("\n")

val GRADLE_METADATA_POM_ANNOTATION = """
  <!-- This module was also published with a richer model, Gradle metadata,  -->
  <!-- which should be used instead. Do not delete the following line which  -->
  <!-- is to indicate to Gradle or any Gradle module metadata file consumer  -->
  <!-- that they should prefer consuming it instead. -->
  <!-- do_not_remove: published-with-gradle-metadata -->
""".trimMargin()

suspend fun <T> retry(
    attempts: Int = 10,
    evaluateDelay: (Int) -> Duration = { 1.seconds },
    timeout: Duration = 5.seconds,
    action: suspend CoroutineScope.() -> T
): T {
    var currentAttempt = 0
    while (true) {
        if (currentAttempt < attempts) {
            try {
                return withTimeout(timeout) { action() }
            } catch (e: TimeoutCancellationException) {
                delay(evaluateDelay(currentAttempt))
                currentAttempt++
            }
        } else return withTimeout(timeout) { action() }
    }
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
suspend fun <K, V, R> Map<K, V>.parallelMapNotNull(
    transform: suspend CoroutineScope.(Map.Entry<K, V>) -> R?
) = coroutineScope { map { async { transform(it) } }.awaitAll().filterNotNull() }

suspend fun normalizeNpmPackageVersion(
    dependencyName: String,
    dependencyVersion: String,
    requestingPackagePublicationDate: Instant,
    client: HttpClient
): String? {

    val range = runCatching { Range(dependencyVersion, true) }.getOrNull() ?: return null

    val metadata: NpmPackageMetadata = retry { client.get("https://registry.npmjs.org/$dependencyName").body() }

    return metadata.versions.keys
        .asSequence()
        .map { SemVer(it, true) }
        .filter {
            when (dependencyVersion) {
                "latest" -> true
                else -> it in range
            }
        }
        .map { it.toString() }
        .filter { metadata.time.getValue(it) < requestingPackagePublicationDate }
        .sortedDescending()
        .firstOrNull()

}

operator fun Range.contains(version: SemVer): Boolean = test(version)

fun Hash.digestAsHex(data: String) =
    update(data).digest("hex")

fun Hash.digestAsHex(data: ByteArray) =
    update(Uint8Array(data.toTypedArray())).digest("hex")