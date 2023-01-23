package com.nowiwr01p.domain.auth.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.verification.repository.VerificationLocalRepository

class GetLocalVerificationUseCase(
    private val repository: VerificationLocalRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return repository.isVerificationCompleted()
    }
}