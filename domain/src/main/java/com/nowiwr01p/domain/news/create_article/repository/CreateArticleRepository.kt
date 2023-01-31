package com.nowiwr01p.domain.news.create_article.repository

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.news.article.data.CreateArticleMode

interface CreateArticleRepository {
    suspend fun createArticle(mode: CreateArticleMode, article: Article)
}