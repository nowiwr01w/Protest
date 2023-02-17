package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository

class SubscribeMeetingsUseCase(
    private val client: MeetingsRepository
): UseCase<Unit, ReferencedListener> {

    override suspend fun execute(input: Unit): ReferencedListener {
        return client.subscribeMeetings()
    }
}