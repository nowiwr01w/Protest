package com.nowiwr01p.domain.stories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.stories.repository.StoriesRepository
import kotlinx.coroutines.flow.StateFlow

class GetStoriesViewersUseCase(
    private val repository: StoriesRepository
): UseCase<Unit, StateFlow<Map<String, List<String>>>> {

    override suspend fun execute(input: Unit): StateFlow<Map<String, List<String>>> {
        return repository.getStoriesViewers()
    }
}