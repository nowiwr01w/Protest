package com.nowiwr01p.news.navigation

import com.nowiwr01p.core_ui.Keys

enum class NewsScreenType(val route: String) {
    NewsMainScreen("news_main_screen"),
    NewsArticleScreen("news_article_screen?chosen_article={${Keys.ARG_ARTICLE}}")
}