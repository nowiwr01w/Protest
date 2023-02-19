package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.stories.repository.StoriesRepository

class SetStoryViewedUseCase(
    private val client: StoriesRepository
): UseCase<String, Unit> {

    override suspend fun execute(input: String) {
        client.setStoryViewed(input)
    }
}