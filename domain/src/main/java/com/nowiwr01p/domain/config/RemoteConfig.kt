package com.nowiwr01p.domain.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nowiwr01p.domain.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

abstract class RemoteConfig: KoinComponent {

    protected val config = get<FirebaseRemoteConfig>()
    protected val dispatchers = get<AppDispatchers>()

    protected abstract fun initValues()

    init {
        CoroutineScope(dispatchers.io).launch {
            initConfig()
        }
    }

    private fun initConfig() {
        config.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                initValues()
            }
        }
    }
}