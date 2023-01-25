package com.nowiwr01p.domain.stories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.stories.client.StoriesClient

class SubscribeStoriesUseCase(
    private val client: StoriesClient
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        client.subscribeStories()
    }
}