package org.jetbrains.gradle.data

data class NpmArtifactHashes(
    val coordinates: NpmArtifactCoordinates,
    val tarballSha512: String,
    val tarballSha256: String,
    val tarballSha1: String,
    val tarballMd5: String,
    val pomSha1: String,
    val moduleSha1: String,
    val tarballSize: Long
)