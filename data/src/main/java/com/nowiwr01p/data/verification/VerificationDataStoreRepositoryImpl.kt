package com.nowiwr01p.data.verification

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.verification.VerificationPreference
import com.nowiwr01p.domain.verification.repository.VerificationLocalRepository
import kotlinx.coroutines.flow.first

class VerificationDataStoreRepositoryImpl(
    private val verificationStore: DataStore<VerificationPreference>
): VerificationLocalRepository {

    override suspend fun isVerificationCompleted(): Boolean {
        return verificationStore.data.first().completed
    }

    override suspend fun setVerificationCompleted(completed: Boolean) {
        verificationStore.updateData {
            VerificationPreference(completed)
        }
    }
}