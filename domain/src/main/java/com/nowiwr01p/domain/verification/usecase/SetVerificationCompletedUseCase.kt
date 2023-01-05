package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationLocalRepository
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository

class SetVerificationCompletedUseCase(
    private val localRepository: VerificationLocalRepository,
    private val remoteRepository: VerificationRemoteRepository
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        remoteRepository.setVerificationCompleted()
        localRepository.setVerificationCompleted()
    }
}