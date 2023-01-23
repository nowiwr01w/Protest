package com.nowiwr01p.domain.auth.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.main.data.error.AuthError
import com.nowiwr01p.domain.auth.main.data.user.UserData
import com.nowiwr01p.domain.auth.main.repository.ValidateAuthDataRepository

class ValidateAuthDataUseCase(
    private val repository: ValidateAuthDataRepository
): UseCase<UserData, AuthError?> {

    override suspend fun execute(input: UserData): AuthError? {
        return repository.isAuthDataValid(input)
    }
}