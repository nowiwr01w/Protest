package com.nowiwr01p.domain.map

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.repository.UserDataStoreRepository

class GetLocalUserUseCase(
    private val repository: UserDataStoreRepository
): UseCase<Unit, User> {

    override suspend fun execute(input: Unit): User {
        return repository.getUser()
    }
}