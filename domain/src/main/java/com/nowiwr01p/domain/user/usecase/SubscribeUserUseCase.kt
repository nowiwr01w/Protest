package com.nowiwr01p.domain.user.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository

class SubscribeUserUseCase(
    private val client: UserRemoteRealtimeRepository
): UseCase<Unit, ReferencedListener> {

    override suspend fun execute(input: Unit): ReferencedListener {
        return client.subscribeUser()
    }
}