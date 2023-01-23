package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.repository.UserLocalRepository

class GetLocalUserUseCase(
    private val repository: UserLocalRepository
): UseCase<Unit, User> {

    override suspend fun execute(input: Unit): User {
        return repository.getUser()
    }
}