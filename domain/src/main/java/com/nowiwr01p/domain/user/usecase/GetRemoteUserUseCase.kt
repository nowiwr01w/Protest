package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class GetRemoteUserUseCase(
    private val repository: UserRemoteRepository
): UseCase<Unit, User> {

    override suspend fun execute(input: Unit): User {
        return repository.getUser()
    }
}