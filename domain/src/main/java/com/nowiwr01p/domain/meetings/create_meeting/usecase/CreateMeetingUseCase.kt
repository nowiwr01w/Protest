package com.nowiwr01p.domain.meetings.create_meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository

class CreateMeetingUseCase(
    private val repository: MeetingsRepository
): UseCase<Meeting, Unit> {

    override suspend fun execute(input: Meeting) {
        repository.createMeeting(input)
    }
}