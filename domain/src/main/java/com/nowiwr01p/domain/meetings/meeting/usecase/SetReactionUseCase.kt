package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsClient
import com.nowiwr01p.domain.meetings.meeting.usecase.SetReactionUseCase.*

class SetReactionUseCase(
    private val repository: MeetingsClient
): UseCase<Args, Meeting> {

    override suspend fun execute(input: Args): Meeting {
        return repository.setReaction(input.meetingId, input.reaction)
    }

    data class Args(
        val meetingId: String,
        val reaction: Boolean
    )
}