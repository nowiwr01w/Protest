package com.nowiwr01p.domain.news.repository

import com.nowiwr01p.core.model.Article

interface NewsRepository {
    suspend fun getNews(): List<Article>
}