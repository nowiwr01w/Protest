package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository

class GetMeetingsUseCase(
    private val repository: MeetingsRepository
): UseCase<Unit, List<Meeting>> {

    override suspend fun execute(input: Unit): List<Meeting> {
        return repository.getMeetings()
    }
}