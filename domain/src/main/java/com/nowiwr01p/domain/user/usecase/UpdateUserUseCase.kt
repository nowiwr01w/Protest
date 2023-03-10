package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class UpdateUserNameUseCase(
    private val getUserUseCase: GetUserUseCase,
    private val userRemoteRepository: UserRemoteRepository
): UseCase<String, User> {

    override suspend fun execute(input: String): User {
        val updated = getUserUseCase.execute().value.copy(name = input)
        return userRemoteRepository.setUser(updated)
    }
}