package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository

class FirebaseSendVerificationUseCase(
    private val remoteRepository: VerificationRemoteRepository
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        remoteRepository.sendVerification()
    }
}