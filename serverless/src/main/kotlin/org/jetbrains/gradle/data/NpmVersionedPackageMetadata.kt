package org.jetbrains.gradle.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = NpmVersionedPackageMetadata.CustomSerializer::class)
data class NpmVersionedPackageMetadata(
    val name: String,
    val version: String,
    val author: Developer? = null,
    val dist: Distribution,
    val dependencies: Map<String, String> = emptyMap()
) {

    @Serializer(forClass = NpmVersionedPackageMetadata::class)
    object BaseSerializer : KSerializer<NpmVersionedPackageMetadata>

    companion object CustomSerializer : KSerializer<NpmVersionedPackageMetadata> {
        override val descriptor = BaseSerializer.descriptor

        override fun serialize(encoder: Encoder, value: NpmVersionedPackageMetadata) =
            BaseSerializer.serialize(encoder, value)

        override fun deserialize(decoder: Decoder): NpmVersionedPackageMetadata = if (decoder is JsonDecoder) {
            val body = decoder.decodeJsonElement()
            NpmVersionedPackageMetadata(
                name = body.jsonObject.getValue("name").jsonPrimitive.content,
                version = body.jsonObject.getValue("version").jsonPrimitive.content,
                author = runCatching {
                    body.jsonObject["author"]?.let { decoder.json.decodeFromJsonElement<Developer>(it) }
                }.getOrNull(),
                dist = decoder.json.decodeFromJsonElement(body.jsonObject.getValue("dist")),
                dependencies = runCatching {
                    body.jsonObject["dependencies"]?.let {
                        decoder.json.decodeFromJsonElement<Map<String, String>>(it)
                    }
                }.getOrNull() ?: emptyMap()
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
