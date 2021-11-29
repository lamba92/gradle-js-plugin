package com.github.lamba92.npmtomaven.models

import kotlinx.serialization.Serializable


@Serializable
data class GradleMetadata(
    val formatVersion: String = "1.1",
    val component: Component,
    val createdBy: CreatedBy,
    val variants: List<Variant>
) {

    @Serializable
    data class CreatedBy(
        val gradle: Gradle
    ) {
        @Serializable
        data class Gradle(val version: String)
    }

    @Serializable
    data class Component(
        val group: String,
        val module: String,
        val version: String,
        val attributes: Map<String, String>,
    )

    @Serializable
    data class Variant(
        val name: String,
        val attributes: Map<String, String>,
        val dependencies: List<Dependency>,
        val files: List<File>
    ) {

        @Serializable
        data class File(
            val name: String,
            val url: String,
            val size: Long,
            val sha512: String,
            val sha256: String,
            val sha1: String,
            val md5: String
        )

        @Serializable
        data class Dependency(
            val group: String,
            val module: String,
            val version: Version
        ) {

            @Serializable
            data class Version(val requires: String)
        }
    }
}