package com.nowiwr01p.domain.article

interface ArticleRepository {
    suspend fun setArticleViewed(articleId: String): Int
}