package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository
import kotlinx.coroutines.flow.StateFlow

class GetUserUseCase(
    private val client: UserRemoteRealtimeRepository
): UseCase<Unit, StateFlow<User>> {

    override suspend fun execute(input: Unit): StateFlow<User> {
        return client.getUserFlow()
    }
}