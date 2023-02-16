package com.nowiwr01p.core.extenstion

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * ENCODE TO UTF-8
 */
fun String.setArgToRoute(key: String, value: String) = replace(
    oldValue = "{${key}}",
    newValue = URLEncoder.encode(value, "UTF-8")
)

inline fun <reified T> String.setObjectArgToNavigationRoute(key: String, value: T): String {
    return setArgToRoute(key, Json.encodeToString(value))
}

/**
 * DECODE FROM UTF-8
 */
inline fun <reified T> NavBackStackEntry.decodeObjectFromString(key: String): T {
    val json = arguments?.getString(key).orEmpty()
    val decodedJson = URLDecoder.decode(json, "UTF-8")
    return Json.decodeFromString(decodedJson)
}