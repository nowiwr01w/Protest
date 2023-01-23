package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.data.Story
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository

class SetStoryViewedUseCase(
    private val repository: MeetingsRepository
): UseCase<String, Story> {

    override suspend fun execute(input: String): Story {
        return repository.setStoryViewed(input)
    }
}