package com.nowiwr01p.data.app

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.app.AppPreference
import com.nowiwr01p.domain.app.AppDataStoreRepository
import kotlinx.coroutines.flow.first

class AppDataStoreRepositoryImpl(
    private val dataStore: DataStore<AppPreference>
): AppDataStoreRepository {

    override suspend fun getAnimationUrl() = dataStore.data.first().splashScreenAnimationUrl

    override suspend fun setAnimationUrl(url: String) {
        dataStore.updateData { AppPreference(url) }
    }
}