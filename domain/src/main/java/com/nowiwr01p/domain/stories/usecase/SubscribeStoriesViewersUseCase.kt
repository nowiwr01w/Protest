package com.nowiwr01p.domain.stories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.stories.repository.StoriesRepository

class SubscribeStoriesViewersUseCase(
    private val repository: StoriesRepository
): UseCase<Unit, ReferencedListener> {

    override suspend fun execute(input: Unit): ReferencedListener {
        return repository.subscribeStoriesViewers()
    }
}