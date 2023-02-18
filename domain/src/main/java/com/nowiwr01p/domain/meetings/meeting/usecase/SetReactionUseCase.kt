package com.nowiwr01p.domain.meetings.meeting.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.meeting.repository.MeetingRepository
import com.nowiwr01p.domain.meetings.meeting.usecase.SetReactionUseCase.*

class SetReactionUseCase(
    private val client: MeetingRepository
): UseCase<Args, Unit> {

    data class Args(
        val meetingId: String,
        val reaction: Boolean
    )

    override suspend fun execute(input: Args) {
        client.setReaction(input.meetingId, input.reaction)
    }
}