package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository

class GetRemoteVerificationUseCase(
    private val repository: VerificationRemoteRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return repository.isVerificationCompleted()
    }
}