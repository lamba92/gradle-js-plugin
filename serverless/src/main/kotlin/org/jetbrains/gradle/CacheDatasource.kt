package org.jetbrains.gradle

import org.jetbrains.gradle.data.NpmArtifactCoordinates
import org.jetbrains.gradle.data.NpmArtifactHashes
import org.jetbrains.gradle.data.NpmArtifactResult

interface CacheDatasource {
    suspend fun getPom(coordinates: NpmArtifactCoordinates): String?
    suspend fun getGradleMetadata(coordinates: NpmArtifactCoordinates): String?
    suspend fun getHashes(coordinates: NpmArtifactCoordinates): NpmArtifactHashes?

    //    suspend fun get
    suspend fun saveArtifactData(data: NpmArtifactResult)
}
