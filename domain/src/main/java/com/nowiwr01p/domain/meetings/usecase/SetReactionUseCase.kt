package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository
import com.nowiwr01p.domain.meetings.usecase.SetReactionUseCase.*

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