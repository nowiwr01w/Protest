package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationLocalRepository

class IsVerificationCompletedUseCase(
    private val localRepository: VerificationLocalRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return localRepository.isVerificationCompleted()
    }
}