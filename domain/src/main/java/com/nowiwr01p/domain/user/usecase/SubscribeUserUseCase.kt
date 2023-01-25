package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.client.UserClient

class SubscribeUserUseCase(
    private val client: UserClient
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        client.subscribeUser()
    }
}