package com.nowiwr01p.core.datastore.cities

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object CitiesPreferencesSerializer: Serializer<CitiesPreference> {

    override val defaultValue = CitiesPreference()

    override suspend fun writeTo(t: CitiesPreference, output: OutputStream) {
        val string = Json.encodeToString(t)
        val bytes = string.encodeToByteArray()
        output.write(bytes)
    }

    override suspend fun readFrom(input: InputStream): CitiesPreference {
        try {
            val bytes = input.readBytes()
            val string = bytes.decodeToString()
            return Json.decodeFromString(string)
        } catch (error: SerializationException) {
            throw CorruptionException("Cannot read data from DataStore", error)
        }
    }
}