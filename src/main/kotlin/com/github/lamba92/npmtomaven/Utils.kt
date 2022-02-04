@file:OptIn(ExperimentalTime::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmPackageMetadata
import com.github.yuchi.semver.Range
import com.github.yuchi.semver.Version
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

fun String.insertAtLine(index: Int, line: String) =
    lines().toMutableList().apply { add(index, line) }.joinToString("\n")

val GRADLE_METADATA_POM_ANNOTATION = """
  <!-- This module was also published with a richer model, Gradle metadata,  -->
  <!-- which should be used instead. Do not delete the following line which  -->
  <!-- is to indicate to Gradle or any Gradle module metadata file consumer  -->
  <!-- that they should prefer consuming it instead. -->
  <!-- do_not_remove: published-with-gradle-metadata -->
""".trimMargin()

suspend fun normalizeNpmPackageVersion(
    dependencyName: String,
    dependencyVersion: String,
    requestingPackagePublicationDate: Instant,
    client: HttpClient
): String? {

    val range = Range.from(dependencyVersion, true) ?: return null

    val metadata = retry {
        client.get<NpmPackageMetadata>("https://registry.npmjs.org/$dependencyName")
    }

    return metadata.versions.keys
        .asSequence()
        .map { Version(it, true) }
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

suspend fun <T> retry(
    attempts: Int = 10,
    evaluateDelay: (Int) -> Duration =  { 1.seconds },
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

operator fun Range.contains(version: Version): Boolean = test(version)
