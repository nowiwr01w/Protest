package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Navigation.Version.NAVIGATION

object Navigation {

    const val COMPOSE = "androidx.navigation:navigation-compose:$NAVIGATION"
    const val COMMON = "androidx.navigation:navigation-common-ktx:$NAVIGATION"

    private object Version {
        const val NAVIGATION = "2.6.0-alpha04"
    }
}