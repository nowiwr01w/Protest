package com.nowiwr01p.domain.auth.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.data.user.User
import com.nowiwr01p.domain.auth.data.user.UserData
import com.nowiwr01p.domain.auth.repository.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
): UseCase<UserData, User> {

    override suspend fun execute(input: UserData): User {
        return repository.signUp(input)
    }
}