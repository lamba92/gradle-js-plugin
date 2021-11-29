package com.github.lamba92.npmtomaven.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NpmPackageInfo(
    @SerialName("_id") val id: String,
    @SerialName("_rev") val rev: String,
    val bugs: Bugs? = null,
    val description: String,
    @SerialName("dist-tags") val distTags: Map<String, String> = emptyMap(),
    val homepage: String? = null,
    val license: String,
    val maintainers: List<Maintainer> = emptyList(),
    val name: String,
    val readme: String,
    val readmeFilename: String,
    val repository: Repository? = null,
    val time: Map<String, String> = emptyMap(),
    val versions: Map<String, Version> = emptyMap()
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
    val name: String,
    val url: String? = null,
    val version: String
)
