package com.nowiwr01p.domain.news.main.repository

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.app.ReferencedListener
import kotlinx.coroutines.flow.StateFlow

interface NewsRepository {
    suspend fun subscribeNews(): ReferencedListener
    suspend fun getNews(): StateFlow<List<Article>>
    suspend fun getUnpublishedNews(): List<Article>
}