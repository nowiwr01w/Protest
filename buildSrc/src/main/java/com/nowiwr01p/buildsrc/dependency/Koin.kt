package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Koin.Version.KOIN_COMPOSE
import com.nowiwr01p.buildsrc.dependency.Koin.Version.KOIN_MAIN

object Koin {

    const val KOIN_ANDROID = "io.insert-koin:koin-android:$KOIN_MAIN"
    const val KOIN_COMPOSE_LIB = "io.insert-koin:koin-androidx-compose:$KOIN_COMPOSE"

    private object Version {
        const val KOIN_MAIN = "3.3.2"
        const val KOIN_COMPOSE = "3.4.1"
    }
}