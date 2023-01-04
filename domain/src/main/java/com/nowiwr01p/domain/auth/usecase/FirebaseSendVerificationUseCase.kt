package com.nowiwr01p.domain.auth.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.repository.AuthRepository

class FirebaseSendVerificationUseCase(
    private val repository: AuthRepository
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        repository.sendVerification()
    }
}