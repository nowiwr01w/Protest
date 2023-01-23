package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository

class GetMeetingsUseCase(
    private val repository: MeetingsRepository
): UseCase<Unit, List<Meeting>> {

    override suspend fun execute(input: Unit): List<Meeting> {
        return repository.getMeetings()
    }
}