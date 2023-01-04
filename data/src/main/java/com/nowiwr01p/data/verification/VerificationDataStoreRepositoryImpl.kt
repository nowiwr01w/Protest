package com.nowiwr01p.data.verification

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.verification.VerificationPreference
import com.nowiwr01p.domain.verification.repository.VerificationDataStoreRepository
import kotlinx.coroutines.flow.first

class VerificationDataStoreRepositoryImpl(
    private val verificationStore: DataStore<VerificationPreference>
): VerificationDataStoreRepository {

    override suspend fun isVerificationCompleted(): Boolean {
        return verificationStore.data.first().completed
    }

    override suspend fun setVerificationCompleted() {
        verificationStore.updateData {
            VerificationPreference(true)
        }
    }
}