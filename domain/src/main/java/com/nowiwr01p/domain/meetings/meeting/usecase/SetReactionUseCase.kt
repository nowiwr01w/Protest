package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.repository.MeetingRepository
import com.nowiwr01p.domain.meetings.meeting.usecase.SetReactionUseCase.*

class SetReactionUseCase(
    private val client: MeetingRepository
): UseCase<Args, Meeting> {

    data class Args(
        val meetingId: String,
        val reaction: Boolean
    )

    override suspend fun execute(input: Args): Meeting {
        return client.setReaction(input.meetingId, input.reaction)
    }
}