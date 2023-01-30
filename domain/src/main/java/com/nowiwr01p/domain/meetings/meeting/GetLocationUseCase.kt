package com.nowiwr01p.domain.meetings.meeting

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsClient

class GetLocationUseCase(
    private val client: MeetingsClient
): UseCase<String, LocationInfo?> {

    override suspend fun execute(input: String): LocationInfo? {
        return client.getMeetingLocation(input)
    }
}