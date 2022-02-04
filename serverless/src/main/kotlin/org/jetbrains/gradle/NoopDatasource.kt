package org.jetbrains.gradle

import org.jetbrains.gradle.data.NpmArtifactCoordinates

object NoopDatasource : CacheDatasource {
    override suspend fun getArtifactDataFor(coordinates: NpmArtifactCoordinates): NpmArtifactManager.Result? = null

    override suspend fun saveArtifactData(data: NpmArtifactManager.Result) {
        // noop
    }
}