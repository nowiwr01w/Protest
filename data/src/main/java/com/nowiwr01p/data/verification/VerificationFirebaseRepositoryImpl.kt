package com.nowiwr01p.data.verification

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.user.UserRemoteRepository
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class VerificationRemoteRepositoryImpl(
    private val userRepository: UserRemoteRepository,
    private val dispatchers: AppDispatchers
): VerificationRemoteRepository {

    /**
     * SEND VERIFICATION
     */
    override suspend fun sendVerification(): Unit = withContext(dispatchers.io) {
        userRepository.getFirebaseUser().sendEmailVerification().await()
    }

    /**
     * CHECK VERIFICATION
     */
    override suspend fun isVerificationCompleted() = withContext(dispatchers.io) {
        userRepository.getFirebaseUser().reload().await()
        userRepository.getFirebaseUser().isEmailVerified
    }

    /**
     * SET VERIFICATION COMPLETED
     */
    override suspend fun setVerificationCompleted(): Unit = withContext(dispatchers.io) {
        val updated = userRepository.getUser().copy(verified = true)
        userRepository.updateUser(updated)
    }
}