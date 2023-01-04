package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationDataStoreRepository

class SetVerificationCompletedUseCase(
    private val repository: VerificationDataStoreRepository
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        repository.setVerificationCompleted()
    }
}