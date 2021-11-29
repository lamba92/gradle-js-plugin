package com.github.lamba92.npmtomaven.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement


@Serializable
@SerialName("developer")
data class Developer(
    @XmlElement(true) val name: String? = null,
    @XmlElement(true) val email: String? = null
)

@Serializable
data class Author(
    val email: String? = null,
    val name: String? = null
)

@Serializable
data class Bugs(
    val url: String
)

typealias Maintainer = Author

@Serializable
data class Repository(
    val type: String,
    val url: String
)

@Serializable
data class Dist(
    val shasum: String,
    val tarball: String
)

