package com.nowiwr01p.domain.news.usecase.data

import com.nowiwr01p.core.model.Article

data class NewsScreenCacheData(
    val news: List<Article> = listOf()
)