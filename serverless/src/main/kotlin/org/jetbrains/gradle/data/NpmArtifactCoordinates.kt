package org.jetbrains.gradle.data

import externals.semver.SemVer
import externals.semver.parse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

fun NpmArtifactCoordinates(name: String, version: String) =
    NpmArtifactCoordinates(name, parse(version) ?: error("Version $version is not a valid semver"))

@Serializable
data class NpmArtifactCoordinates(
    val name: String,
    @Serializable(with = SemVerSerializer::class) val version: SemVer
) {

    val npmArtifactName
        get() = "$name/${version.version}"

    val mavenArtifactName
        get() = "$name-${version.version}"

    override fun toString() = "$name@${version.version}"
}

object SemVerSerializer : KSerializer<SemVer> {
    override val descriptor =
        PrimitiveSerialDescriptor("semver", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): SemVer {
        val string = decoder.decodeString()
        return parse(string) ?: error("$string is not a valid semver")
    }

    override fun serialize(encoder: Encoder, value: SemVer) {
        encoder.encodeString(value.version)
    }

}
