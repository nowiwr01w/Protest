package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.data.Story
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository

class GetStoriesUseCase(
    private val repository: MeetingsRepository
): UseCase<Unit, List<Story>> {

    override suspend fun execute(input: Unit): List<Story> {
        return repository.getStories()
    }
}