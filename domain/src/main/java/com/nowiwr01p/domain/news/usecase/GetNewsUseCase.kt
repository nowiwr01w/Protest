package com.nowiwr01p.domain.news.usecase

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.repository.NewsRepository

class GetNewsUseCase(
    private val repository: NewsRepository
) : UseCase<Unit, List<Article>> {
    override suspend fun execute(input: Unit): List<Article> {
        return repository.getNews()
    }
}