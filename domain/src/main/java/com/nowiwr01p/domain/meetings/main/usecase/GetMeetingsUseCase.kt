package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository
import kotlinx.coroutines.flow.StateFlow

class GetMeetingsUseCase(
    private val client: MeetingsRepository
): UseCase<Unit, StateFlow<List<Meeting>>> {

    override suspend fun execute(input: Unit): StateFlow<List<Meeting>> {
        return client.getMeetings()
    }
}