package com.nowiwr01p.domain.news.create_article.repository

import com.nowiwr01p.core.model.Article

interface CreateArticleRepository {
    suspend fun createArticle(article: Article)
}