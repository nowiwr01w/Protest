package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Koin.Version.KOIN

object Koin {

    const val KOIN_LIB = "io.insert-koin:koin-android:${KOIN}"
    const val KOIN_COMPOSE = "io.insert-koin:koin-androidx-compose:${KOIN}"

    private object Version {
        const val KOIN = "3.4.1"
    }
}