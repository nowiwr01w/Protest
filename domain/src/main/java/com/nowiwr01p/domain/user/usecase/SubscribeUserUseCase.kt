package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository

class SubscribeUserUseCase(
    private val client: UserRemoteRealtimeRepository
): UseCase<Unit, EventListener> {

    override suspend fun execute(input: Unit): EventListener {
        return client.subscribeUser()
    }
}