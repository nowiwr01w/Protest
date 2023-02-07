package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient

class RunMeetingUseCase(
    private val client: MeetingClient
): UseCase<LocationInfo, Unit> {

    override suspend fun execute(input: LocationInfo) {
        client.runMeeting(input)
    }
}