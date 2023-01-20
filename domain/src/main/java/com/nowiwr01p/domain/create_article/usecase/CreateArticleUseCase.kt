package com.nowiwr01p.domain.create_article.usecase

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.create_article.repository.CreateArticleRepository

class CreateArticleUseCase(
    private val repository: CreateArticleRepository
): UseCase<Article, Unit> {

    override suspend fun execute(input: Article) {
        repository.createArticle(input)
    }
}