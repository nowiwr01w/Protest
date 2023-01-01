package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Network.Version.GSON
import com.nowiwr01p.buildsrc.dependency.Network.Version.KOTLINX_SERIALIZATION
import com.nowiwr01p.buildsrc.dependency.Network.Version.OKHTTP
import com.nowiwr01p.buildsrc.dependency.Network.Version.RETROFIT
import com.nowiwr01p.buildsrc.dependency.Network.Version.RETROFIT_SERIALIZATION

object Network {

    const val RETROFIT_LIB = "com.squareup.retrofit2:retrofit:$RETROFIT"
    const val RETROFIT_SERIALIZATION_LIB = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$RETROFIT_SERIALIZATION"

    const val OKHTTP_LIB = "com.squareup.okhttp3:okhttp:$OKHTTP"
    const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:$OKHTTP"

    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:$KOTLINX_SERIALIZATION"

    const val GSON_LIB = "com.google.code.gson:gson:$GSON"

    private object Version {
        const val RETROFIT = "2.9.0"
        const val RETROFIT_SERIALIZATION = "0.8.0"
        const val OKHTTP = "4.10.0"
        const val KOTLINX_SERIALIZATION = "1.8.0"
        const val GSON = "2.10"
    }
}