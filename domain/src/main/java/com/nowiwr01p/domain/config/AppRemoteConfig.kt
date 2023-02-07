package com.nowiwr01p.domain.config

import com.google.firebase.remoteconfig.ktx.get
import com.nowiwr01p.domain.app.AppDataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppRemoteConfig(
    private val repository: AppDataStoreRepository,
): RemoteConfig() {

    override fun initValues() {
        CoroutineScope(dispatchers.io).launch {
            config[SPLASH_SCREEN_DEMO_ANIMATION].asBoolean().also { demo ->
                repository.setSplashScreenDemoAnimation(demo)
            }
        }
    }

    private companion object {
        const val SPLASH_SCREEN_DEMO_ANIMATION = "splash_screen_demo_animation"
    }
}