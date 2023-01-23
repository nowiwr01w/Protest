package com.nowiwr01p.domain.auth.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.auth.main.data.user.UserData
import com.nowiwr01p.domain.auth.main.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
): UseCase<UserData, User> {

    override suspend fun execute(input: UserData): User {
        return repository.signIn(input)
    }
}