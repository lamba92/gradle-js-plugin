package org.jetbrains.gradle.data

import kotlinx.serialization.Serializable

@Serializable
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

data class NpmArtifactResult(
    val hashes: NpmArtifactHashes,
    val pom: String,
    val module: String
)
