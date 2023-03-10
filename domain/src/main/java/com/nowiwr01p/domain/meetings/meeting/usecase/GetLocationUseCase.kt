package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.repository.MeetingRepository

class GetLocationUseCase(
    private val client: MeetingRepository
): UseCase<String, LocationInfo> {

    override suspend fun execute(input: String): LocationInfo {
        return client.getMeetingLocation(input)
    }
}