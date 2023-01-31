package com.nowiwr01p.domain.news.create_article.usecase

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.article.data.CreateArticleMode
import com.nowiwr01p.domain.news.create_article.repository.CreateArticleRepository
import com.nowiwr01p.domain.news.create_article.usecase.CreateArticleUseCase.*

class CreateArticleUseCase(
    private val repository: CreateArticleRepository
): UseCase<Args, Unit> {

    data class Args(val mode: CreateArticleMode, val article: Article)

    override suspend fun execute(input: Args) {
        repository.createArticle(input.mode, input.article)
    }
}