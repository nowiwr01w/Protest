package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository

class FirebaseCheckEmailVerificationUseCase(
    private val remoteRepository: VerificationRemoteRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return remoteRepository.isVerificationCompleted()
    }
}