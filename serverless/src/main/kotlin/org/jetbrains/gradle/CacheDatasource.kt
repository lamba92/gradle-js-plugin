package org.jetbrains.gradle

import org.jetbrains.gradle.data.NpmArtifactCoordinates

interface CacheDatasource {
    suspend fun getArtifactDataFor(coordinates: NpmArtifactCoordinates): NpmArtifactManager.Result?
    suspend fun saveArtifactData(data: NpmArtifactManager.Result)
}