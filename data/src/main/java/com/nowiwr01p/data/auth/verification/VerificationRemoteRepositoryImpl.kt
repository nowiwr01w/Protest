package com.nowiwr01p.data.auth.verification

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import com.nowiwr01p.domain.auth.verification.repository.VerificationRemoteRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class VerificationRemoteRepositoryImpl(
    private val userRepository: UserRemoteRepository,
    private val dispatchers: AppDispatchers
): VerificationRemoteRepository {

    /**
     * SEND VERIFICATION
     */
    override suspend fun sendEmailVerification(): Unit = withContext(dispatchers.io) {
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
    override suspend fun setVerificationCompleted(completed: Boolean): Unit = withContext(dispatchers.io) {
        val updated = userRepository.getUser().copy(verified = completed)
        userRepository.updateUser(updated)
    }
}