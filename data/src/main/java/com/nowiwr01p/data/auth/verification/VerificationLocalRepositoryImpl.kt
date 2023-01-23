package com.nowiwr01p.data.auth.verification

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.verification.VerificationPreference
import com.nowiwr01p.domain.auth.verification.repository.VerificationLocalRepository
import kotlinx.coroutines.flow.first

class VerificationLocalRepositoryImpl(
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