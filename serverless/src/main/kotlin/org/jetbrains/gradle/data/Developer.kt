package org.jetbrains.gradle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("developer")
data class Developer(
    @XmlElement(true) val name: String? = null,
    @XmlElement(true) val email: String? = null
)