package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsClient

class SubscribeMeetingsUseCase(
    private val client: MeetingsClient
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        client.subscribeMeetings()
    }
}