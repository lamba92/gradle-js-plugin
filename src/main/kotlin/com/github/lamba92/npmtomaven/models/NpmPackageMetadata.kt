package com.github.lamba92.npmtomaven.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NpmPackageMetadata(
    val versions: Map<String, Version> = emptyMap(),
    val time: Map<String, Instant> = emptyMap()
)

@Serializable
data class Version(
    @SerialName("_defaultsLoaded") val defaultsLoaded: Boolean? = null,
    @SerialName("_engineSupported") val engineSupported: Boolean? = null,
    @SerialName("_id") val id: String,
    @SerialName("_nodeVersion") val nodeVersion: String? = null,
    @SerialName("_npmVersion") val npmVersion: String? = null,
    val lib: String? = null,
    val main: String? = null,
    val name: String? = null,
    val url: String? = null,
    val version: String? = null
)
