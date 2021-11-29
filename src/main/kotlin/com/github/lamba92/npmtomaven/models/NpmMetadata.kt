package com.github.lamba92.npmtomaven.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NpmMetadata(
    val name: String,
    val version: String,
    val author: Developer? = null,
    val dist: Distribution,
    val dependencies: Map<String, String> = emptyMap()
) {

    @Serializable
    data class Distribution(
        val integrity: String? = null,
        val shasum: String? = null,
        val tarball: String,
        val fileCount: Long? = null,
        val unpackedSize: Long? = null,
        @SerialName("npm-signature") val npmSignature: String? = null
    )
}