package com.nowiwr01p.domain.news.main.repository

import com.nowiwr01p.core.model.Article

interface NewsRepository {
    suspend fun getNews(): List<Article>
}