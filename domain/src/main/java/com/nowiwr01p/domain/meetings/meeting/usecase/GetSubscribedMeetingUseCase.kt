package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient
import kotlinx.coroutines.flow.StateFlow

class GetSubscribedMeetingUseCase(
    private val client: MeetingClient
): UseCase<Unit, StateFlow<Meeting>> {

    override suspend fun execute(input: Unit): StateFlow<Meeting> {
        return client.getMeetingFlow()
    }
}