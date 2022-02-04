package org.jetbrains.gradle.data

import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = NpmVersionedPackageMetadata.Companion::class)
data class NpmVersionedPackageMetadata(
    val name: String,
    val version: String,
    val author: Developer? = null,
    val dist: Distribution,
    val dependencies: Map<String, String> = emptyMap()
) {

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = NpmVersionedPackageMetadata::class)
    object BaseSerializer : KSerializer<NpmVersionedPackageMetadata>

    companion object : KSerializer<NpmVersionedPackageMetadata> {
        override val descriptor = BaseSerializer.descriptor

        override fun serialize(encoder: Encoder, value: NpmVersionedPackageMetadata) =
            BaseSerializer.serialize(encoder, value)

        override fun deserialize(decoder: Decoder): NpmVersionedPackageMetadata = if (decoder is JsonDecoder) {
            val body = decoder.decodeJsonElement()
            NpmVersionedPackageMetadata(
                body.jsonObject.getValue("name").jsonPrimitive.content,
                body.jsonObject.getValue("version").jsonPrimitive.content,
                runCatching { body.jsonObject["author"]?.let { decoder.json.decodeFromJsonElement<Developer>(it) } }.getOrNull(),
                decoder.json.decodeFromJsonElement(body.jsonObject.getValue("dist")),
                decoder.json.decodeFromJsonElement(body.jsonObject.getValue("dependencies"))
            )
        } else BaseSerializer.deserialize(decoder)
    }

    @Serializable
    data class Distribution(
        val integrity: String? = null,
        val shasum: String? = null,
        val tarball: String,
        val fileCount: Long? = null,
        val unpackedSize: Long? = null,
        @SerialName("npm-signature") val npmSignature: String? = null
    )
}