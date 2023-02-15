package com.nowiwr01p.domain.news.main.repository

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.app.EventListener
import kotlinx.coroutines.flow.StateFlow

interface NewsClient {
    suspend fun subscribeNews(): EventListener
    suspend fun getNews(): StateFlow<List<Article>>
    suspend fun getUnpublishedNews(): List<Article>
}