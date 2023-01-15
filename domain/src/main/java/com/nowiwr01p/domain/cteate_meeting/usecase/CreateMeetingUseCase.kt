package com.nowiwr01p.domain.cteate_meeting.usecase

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository

class CreateMeetingUseCase(
    private val repository: MeetingsRepository
): UseCase<Meeting, Unit> {

    override suspend fun execute(input: Meeting) {
        repository.createMeeting(input)
    }
}