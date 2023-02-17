package com.nowiwr01p.domain.stories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.data.Story
import com.nowiwr01p.domain.stories.repository.StoriesRepository
import kotlinx.coroutines.flow.StateFlow

class GetStoriesUseCase(
    private val client: StoriesRepository
): UseCase<Unit, StateFlow<List<Story>>> {

    override suspend fun execute(input: Unit): StateFlow<List<Story>> {
        return client.getStories()
    }
}