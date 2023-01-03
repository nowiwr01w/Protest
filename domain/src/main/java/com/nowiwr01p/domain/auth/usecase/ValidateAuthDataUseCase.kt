package com.nowiwr01p.domain.auth.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.data.error.AuthError
import com.nowiwr01p.domain.auth.data.user.UserData
import com.nowiwr01p.domain.auth.repository.ValidateAuthDataRepository

class ValidateAuthDataUseCase(
    private val repository: ValidateAuthDataRepository
): UseCase<UserData, AuthError?> {

    override suspend fun execute(input: UserData): AuthError? {
        return repository.isAuthDataValid(input)
    }
}