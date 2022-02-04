package com.github.lamba92.npmtomaven.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName


fun MavenPom(
    modelVersion: String = "4.0.0",
    groupId: String,
    artifactId: String,
    version: String,
    packaging: String,
    developers: List<Developer> = emptyList(),
    dependencies: List<MavenPom.Dependency> = emptyList()
) = MavenPom(
    modelVersion = modelVersion,
    groupId = groupId,
    artifactId = artifactId,
    version = version,
    packaging = packaging,
    developers = MavenPom.Developers(developers),
    dependencies = MavenPom.Dependencies(dependencies)
)

@Serializable
@XmlSerialName("project", "http://maven.apache.org/POM/4.0.0", "")
data class MavenPom(
    @XmlElement(true) val modelVersion: String = "4.0.0",
    @XmlElement(true) val groupId: String,
    @XmlElement(true) val artifactId: String,
    @XmlElement(true) val version: String,
    @XmlElement(true) val packaging: String,
    @XmlElement(true) val developers: Developers = Developers(),
    @XmlElement(true) val dependencies: Dependencies = Dependencies(),
    @XmlElement(false) @XmlSerialName("schemaLocation", "http://www.w3.org/2001/XMLSchema-instance", "xsi")
    val xsiSchemaLocation: String = "http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
) {


    @Serializable
    @SerialName("developers")
    data class Developers(@XmlElement(true) val elements: List<Developer> = emptyList())

    @Serializable
    @SerialName("dependencies")
    data class Dependencies(@XmlElement(true) val elements: List<Dependency> = emptyList())

    @Serializable
    @SerialName("dependency")
    data class Dependency(
        @XmlElement(true) val groupId: String,
        @XmlElement(true) val artifactId: String,
        @XmlElement(true) val version: String,
        @XmlElement(true) val scope: String
    )
}