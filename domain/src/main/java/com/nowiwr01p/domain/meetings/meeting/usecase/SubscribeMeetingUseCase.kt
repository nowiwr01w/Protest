package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient

class SubscribeMeetingUseCase(
    private val client: MeetingClient
): UseCase<String, Unit> {

    override suspend fun execute(input: String) {
        client.subscribeMeeting(input)
    }
}