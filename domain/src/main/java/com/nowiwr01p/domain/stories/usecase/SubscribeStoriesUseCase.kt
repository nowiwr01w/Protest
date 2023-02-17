package com.nowiwr01p.domain.stories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.stories.repository.StoriesRepository

class SubscribeStoriesUseCase(
    private val client: StoriesRepository
): UseCase<Unit, EventListener> {

    override suspend fun execute(input: Unit): EventListener {
        return client.subscribeStories()
    }
}