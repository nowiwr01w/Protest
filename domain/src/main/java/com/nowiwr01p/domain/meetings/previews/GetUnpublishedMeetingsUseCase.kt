package com.nowiwr01p.domain.meetings.previews

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository

class GetUnpublishedMeetingsUseCase(
    private val client: MeetingsRepository
): UseCase<Unit, List<Meeting>> {

    override suspend fun execute(input: Unit): List<Meeting> {
        return client.getUnpublishedMeetings()
    }
}