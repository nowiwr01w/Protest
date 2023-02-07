package com.nowiwr01p.core.datastore.app

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object AppPreferencesSerializer: Serializer<AppPreference> {

    override val defaultValue = AppPreference()

    override suspend fun writeTo(t: AppPreference, output: OutputStream) {
        val string = Json.encodeToString(t)
        val bytes = string.encodeToByteArray()
        output.write(bytes)
    }

    override suspend fun readFrom(input: InputStream): AppPreference {
        try {
            val bytes = input.readBytes()
            val string = bytes.decodeToString()
            return Json.decodeFromString(string)
        } catch (error: SerializationException) {
            throw CorruptionException("Cannot read AuthSecurityWarning from DataStore", error)
        }
    }
}