package com.nowiwr01p.domain.news.previews

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.main.repository.NewsClient

class GetUnpublishedNewsUseCase(
    private val client: NewsClient
): UseCase<Unit, List<Article>> {

    override suspend fun execute(input: Unit): List<Article> {
        return client.getUnpublishedNews()
    }
}