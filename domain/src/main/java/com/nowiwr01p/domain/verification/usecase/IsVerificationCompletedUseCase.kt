package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationDataStoreRepository

class IsVerificationCompletedUseCase(
    private val repository: VerificationDataStoreRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return repository.isVerificationCompleted()
    }
}