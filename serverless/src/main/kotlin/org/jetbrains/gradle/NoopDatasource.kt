package org.jetbrains.gradle

import org.jetbrains.gradle.data.NpmArtifactCoordinates
import org.jetbrains.gradle.data.NpmArtifactHashes
import org.jetbrains.gradle.data.NpmArtifactResult

object NoopDatasource : CacheDatasource {
    override suspend fun getPom(coordinates: NpmArtifactCoordinates): String? = null

    override suspend fun getGradleMetadata(coordinates: NpmArtifactCoordinates): String? = null

    override suspend fun getHashes(coordinates: NpmArtifactCoordinates): NpmArtifactHashes? = null

    override suspend fun saveArtifactData(data: NpmArtifactResult) {
        // noop
    }
}
