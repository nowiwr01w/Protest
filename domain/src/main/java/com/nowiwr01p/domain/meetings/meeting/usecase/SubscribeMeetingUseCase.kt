package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient
import kotlinx.coroutines.flow.StateFlow

class SubscribeMeetingUseCase(
    private val client: MeetingClient
): UseCase<String, StateFlow<Meeting>> {

    override suspend fun execute(input: String): StateFlow<Meeting> {
        return client.subscribeMeeting(input)
    }
}