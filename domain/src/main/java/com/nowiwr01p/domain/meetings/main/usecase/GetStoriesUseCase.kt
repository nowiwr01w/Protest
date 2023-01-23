package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.data.Story
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository

class GetStoriesUseCase(
    private val repository: MeetingsRepository
): UseCase<Unit, List<Story>> {

    override suspend fun execute(input: Unit): List<Story> {
        return repository.getStories()
    }
}