package org.jetbrains.gradle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("developer")
data class Developer(
    val name: String? = null,
    val email: String? = null
)
