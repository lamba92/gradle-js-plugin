package org.jetbrains.gradle.data

import SemVer

data class NpmArtifactCoordinates(val name: String, val version: SemVer) {

    val key
        get() = "$name/${version.version}"

    override fun toString() = "$name@${version.version}"
}