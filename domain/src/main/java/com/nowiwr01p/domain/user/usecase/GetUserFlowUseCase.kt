package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.client.UserClient
import kotlinx.coroutines.flow.StateFlow

class GetUserFlowUseCase(
    private val client: UserClient
): UseCase<Unit, StateFlow<User>> {

    override suspend fun execute(input: Unit): StateFlow<User> {
        return client.getUserFlow()
    }
}