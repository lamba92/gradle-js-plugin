package org.jetbrains.gradle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
data class MavenPom(
    val modelVersion: String = "4.0.0",
    val groupId: String,
    val artifactId: String,
    val version: String,
    val packaging: String,
    val developers: Developers = Developers(),
    val dependencies: Dependencies = Dependencies(),
    val xsiSchemaLocation: String = "http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
) {

    @Serializable
    @SerialName("developers")
    data class Developers(val elements: List<Developer> = emptyList())

    @Serializable
    @SerialName("dependencies")
    data class Dependencies(val elements: List<Dependency> = emptyList())

    @Serializable
    @SerialName("dependency")
    data class Dependency(
        val groupId: String,
        val artifactId: String,
        val version: String,
        val scope: String
    )

    fun toXml() = """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="$xsiSchemaLocation">
  <modelVersion>$modelVersion</modelVersion>
  <artifactId>$artifactId</artifactId>
  <version>$version</version>
  <packaging>$packaging</packaging>
  <developers>
${
        developers.elements.joinToString("") {
            """  <developer>
      <name>${it.name}</name>
      <email>${it.email}</email>
  </developer>"""
        }
    }
  </developers>
  <dependencies>
${
        dependencies.elements.joinToString("\n") {
            """  <dependency>
    <groupId>${it.groupId}</groupId>
    <artifactId>${it.artifactId}</artifactId>
    <version>${it.version}</version>
  </dependency>"""
        }
    }
    </dependencies>
</project>"""
}
