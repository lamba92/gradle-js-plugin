package org.jetbrains.gradle

import org.jetbrains.gradle.data.NpmArtifactCoordinates

interface CacheDatasource {
    suspend fun getPom(coordinates: NpmArtifactCoordinates): String?
    suspend fun getModule(coordinates: NpmArtifactCoordinates): String?

    //    suspend fun get
    suspend fun saveArtifactData(data: NpmArtifactManager.Result)
}