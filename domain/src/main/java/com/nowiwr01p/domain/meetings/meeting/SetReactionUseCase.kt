package com.nowiwr01p.domain.meetings.meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository
import com.nowiwr01p.domain.meetings.meeting.SetReactionUseCase.*

class SetReactionUseCase(
    private val repository: MeetingsRepository
): UseCase<Args, Meeting> {

    override suspend fun execute(input: Args): Meeting {
        return repository.setReaction(input.meetingId, input.reaction)
    }

    data class Args(
        val meetingId: String,
        val reaction: Boolean
    )
}