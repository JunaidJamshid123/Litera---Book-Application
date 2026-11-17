package com.example.litera.book.data.remote.dto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class BookDetailsDto(
    @SerialName("key") val key: String,
    @SerialName("title") val title: String? = null,
    @Serializable(with = DescriptionSerializer::class)
    @SerialName("description") val description: String? = null,
    @SerialName("subjects") val subjects: List<String>? = null,
    @SerialName("covers") val covers: List<Int>? = null,
    @SerialName("first_publish_date") val firstPublishDate: String? = null
)

object DescriptionSerializer : KSerializer<String?> {
    override val descriptor = JsonElement.serializer().descriptor

    override fun deserialize(decoder: Decoder): String? {
        val element = (decoder as JsonDecoder).decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> element.contentOrNull
            is JsonObject -> element["value"]?.jsonPrimitive?.contentOrNull
            else -> null
        }
    }

    override fun serialize(encoder: Encoder, value: String?) {
        encoder.encodeString(value.orEmpty())
    }
}