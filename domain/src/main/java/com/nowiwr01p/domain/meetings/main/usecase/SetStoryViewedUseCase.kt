package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.data.Story
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository

class SetStoryViewedUseCase(
    private val repository: MeetingsRepository
): UseCase<String, Story> {

    override suspend fun execute(input: String): Story {
        return repository.setStoryViewed(input)
    }
}