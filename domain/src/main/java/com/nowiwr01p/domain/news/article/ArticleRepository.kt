package com.nowiwr01p.domain.news.article

interface ArticleRepository {
    suspend fun setArticleViewed(articleId: String): Int
}