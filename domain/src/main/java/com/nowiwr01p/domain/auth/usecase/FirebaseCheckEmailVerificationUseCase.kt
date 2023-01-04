package com.nowiwr01p.domain.auth.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.repository.AuthRepository

class FirebaseCheckEmailVerificationUseCase(
    private val repository: AuthRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return repository.checkVerification()
    }
}