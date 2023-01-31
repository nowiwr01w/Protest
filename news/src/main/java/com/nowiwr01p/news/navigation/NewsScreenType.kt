package com.nowiwr01p.news.navigation

import com.nowiwr01p.core_ui.Keys

enum class NewsScreenType(val route: String) {
    NewsScreen("news_screen"),
    UnpublishedNewsScreen("unpublished_news_screen"),
    CreateArticleScreen("create_article_screen"),
    ArticleScreen("article_screen?chosen_article={${Keys.ARG_ARTICLE}}")
}