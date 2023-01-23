package com.nowiwr01p.domain.news.article

import com.nowiwr01p.domain.UseCase

class SetArticleViewedUseCase(
    private val repository: ArticleRepository
): UseCase<String, Int> {

    override suspend fun execute(input: String): Int {
        return repository.setArticleViewed(input)
    }
}