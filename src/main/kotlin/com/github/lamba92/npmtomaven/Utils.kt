package com.github.lamba92.npmtomaven

import com.github.lamba92.npmtomaven.models.NpmPackageInfo
import com.github.yuchi.semver.Range
import com.github.yuchi.semver.Version
import io.ktor.client.*
import io.ktor.client.request.*

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
    client: HttpClient
): String? {

    val versions = client.get<NpmPackageInfo>("https://registry.npmjs.org/$dependencyName")
        .versions.keys
        .asSequence()
        .map { Version(it, true) }

    val range = Range.from(dependencyVersion, true)

    return when (dependencyVersion) {
        "latest" -> versions.sortedDescending().firstOrNull()
        else -> versions.filter { range.test(it) }
            .sortedDescending()
            .firstOrNull()
    }?.toString()

}