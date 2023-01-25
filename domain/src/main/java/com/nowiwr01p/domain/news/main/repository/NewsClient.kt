package com.nowiwr01p.domain.news.main.repository

import com.nowiwr01p.core.model.Article
import kotlinx.coroutines.flow.StateFlow

interface NewsClient {
    suspend fun subscribeNews()
    suspend fun getNews(): StateFlow<List<Article>>
}