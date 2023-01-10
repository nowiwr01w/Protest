package com.nowiwr01p.core.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = ArticleContentTypeSerializer::class)
enum class ArticleContentType(val type: String) {
    Title("title"),
    Date("date"),
    Image("image"),
    Description("description"),
    Step("step"),
    Link("link"),
    OrderedListTitle("orderedListTitle");

    companion object {
        fun findByType(type: String) = values().find {
            it.type.equals(type, ignoreCase = true)
        } ?: Description
    }

}

@ExperimentalSerializationApi
@Serializer(forClass = ArticleContentType::class)
object ArticleContentTypeSerializer : KSerializer<ArticleContentType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(ArticleContentType::class.java.name, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ArticleContentType) {
        encoder.encodeString(value.type)
    }

    override fun deserialize(decoder: Decoder): ArticleContentType {
        return try {
            val type = decoder.decodeString()
            ArticleContentType.findByType(type)
        } catch (e: IllegalArgumentException) {
            ArticleContentType.Description
        }
    }
}