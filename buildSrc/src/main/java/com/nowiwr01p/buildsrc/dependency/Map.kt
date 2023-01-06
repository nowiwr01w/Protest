package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Map.Version.GOOGLE_MAP_VERSION
import com.nowiwr01p.buildsrc.dependency.Map.Version.PLAY_SERVICES_VERSION

object Map {

    const val GOOGLE_MAP = "com.google.maps.android:maps-compose:$GOOGLE_MAP_VERSION"
    const val PLAY_SERVICES = "com.google.android.gms:play-services-maps:$PLAY_SERVICES_VERSION"
    const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:${BuildConfig.COMPOSE_VERSION}"
    const val GOOGLE_MAP_WIDGETS = "com.google.maps.android:maps-compose-widgets:$GOOGLE_MAP_VERSION"

    private object Version {
        const val GOOGLE_MAP_VERSION = "2.8.0"
        const val PLAY_SERVICES_VERSION = "18.0.2"
    }
}