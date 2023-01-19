package com.nowiwr01p.domain.create_article

import com.nowiwr01p.core.model.Article

interface CreateArticleRepository {
    suspend fun createArticle(article: Article)
}