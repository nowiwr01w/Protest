package com.nowiwr01p.core.datastore.app

import kotlinx.serialization.Serializable

@Serializable
data class AppPreference(
    val splashScreenDemoAnimation: Boolean = false
)