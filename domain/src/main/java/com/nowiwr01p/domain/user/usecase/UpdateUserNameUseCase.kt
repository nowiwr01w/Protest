package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class UpdateUserNameUseCase(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val userRemoteRepository: UserRemoteRepository
): UseCase<String, User> {

    override suspend fun execute(input: String): User {
        val updated = getLocalUserUseCase.execute().copy(name = input)
        return userRemoteRepository.updateUser(updated)
    }
}