package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.data.Story
import com.nowiwr01p.domain.stories.client.StoriesClient

class SetStoryViewedUseCase(
    private val client: StoriesClient
): UseCase<String, Story> {

    override suspend fun execute(input: String): Story {
        return client.setStoryViewed(input)
    }
}