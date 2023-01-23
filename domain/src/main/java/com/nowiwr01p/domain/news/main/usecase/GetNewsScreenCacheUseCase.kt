package com.nowiwr01p.domain.news.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.main.usecase.data.NewsScreenCache

class GetNewsScreenCacheUseCase(
    private val newsScreenCache: NewsScreenCache
): UseCase<Unit, NewsScreenCache> {

    override suspend fun execute(input: Unit): NewsScreenCache {
        return newsScreenCache
    }
}