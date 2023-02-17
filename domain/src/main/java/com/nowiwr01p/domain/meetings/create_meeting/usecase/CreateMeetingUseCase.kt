package com.nowiwr01p.domain.meetings.create_meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.create_meeting.repository.CreateMeetingRepository
import com.nowiwr01p.domain.meetings.create_meeting.usecase.CreateMeetingUseCase.*
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode

class CreateMeetingUseCase(
    private val repository: CreateMeetingRepository
): UseCase<Args, Unit> {

    data class Args(
        val mode: CreateMeetingMode,
        val meeting: Meeting
    )

    override suspend fun execute(input: Args) {
        repository.createMeeting(input.mode, input.meeting)
    }
}