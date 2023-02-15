package com.nowiwr01p.core.datastore.app

import kotlinx.serialization.Serializable

@Serializable
data class AppPreference(
    val splashScreenAnimationUrl: String = "https://assets6.lottiefiles.com/packages/lf20_Lkj6LJfM8f.json"
)