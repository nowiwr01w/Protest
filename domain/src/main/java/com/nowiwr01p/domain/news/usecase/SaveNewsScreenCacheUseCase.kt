package com.nowiwr01p.domain.news.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.usecase.data.NewsScreenCache
import com.nowiwr01p.domain.news.usecase.data.NewsScreenCacheData

class SaveNewsScreenCacheUseCase(
    private val newsScreenCache: NewsScreenCache
) : UseCase<NewsScreenCacheData, Unit> {

    override suspend fun execute(input: NewsScreenCacheData) {
        newsScreenCache.data = input
    }
}