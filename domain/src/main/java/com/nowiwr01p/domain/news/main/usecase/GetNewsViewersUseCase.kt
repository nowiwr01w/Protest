package com.nowiwr01p.domain.news.main.usecase

import com.nowiwr01p.core.model.DateViewers
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.main.repository.NewsRepository
import kotlinx.coroutines.flow.StateFlow

class GetNewsViewersUseCase(
    private val repository: NewsRepository
): UseCase<Unit, StateFlow<Map<String, DateViewers>>> {

    override suspend fun execute(input: Unit): StateFlow<Map<String, DateViewers>> {
        return repository.getNewsViewers()
    }
}