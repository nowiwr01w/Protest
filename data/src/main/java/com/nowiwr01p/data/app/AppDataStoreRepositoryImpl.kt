package com.nowiwr01p.data.app

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.app.AppPreference
import com.nowiwr01p.domain.app.AppDataStoreRepository
import kotlinx.coroutines.flow.first

class AppDataStoreRepositoryImpl(
    private val dataStore: DataStore<AppPreference>
): AppDataStoreRepository {

    override suspend fun isSplashScreenDemoAnimation() = dataStore.data.first().splashScreenDemoAnimation

    override suspend fun setSplashScreenDemoAnimation(demo: Boolean) {
        dataStore.updateData { AppPreference(demo) }
    }
}