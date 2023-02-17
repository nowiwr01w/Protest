package com.nowiwr01p.domain.news.main.usecase

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.main.repository.NewsRepository
import kotlinx.coroutines.flow.StateFlow

class GetNewsUseCase(
    private val repository: NewsRepository
) : UseCase<Unit, StateFlow<List<Article>>> {

    override suspend fun execute(input: Unit): StateFlow<List<Article>> {
        return repository.getNews()
    }
}