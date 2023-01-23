package com.nowiwr01p.data.auth.main.repository

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.auth.AuthSecurityWarning
import com.nowiwr01p.domain.auth.main.repository.AuthSecurityDataStoreRepository
import kotlinx.coroutines.flow.first

class AuthSecurityDataStoreRepositoryImpl(
    private val dataStore: DataStore<AuthSecurityWarning>
): AuthSecurityDataStoreRepository {

    override suspend fun isAuthSecurityWarningShown() = dataStore.data.first().shown

    override suspend fun setAuthSecurityWarningShown() {
        dataStore.updateData {
            AuthSecurityWarning(true)
        }
    }
}