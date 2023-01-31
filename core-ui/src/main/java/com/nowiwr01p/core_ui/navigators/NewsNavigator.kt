package com.nowiwr01p.core_ui.navigators

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.navigators.module.ModuleNavigator

interface NewsNavigator: ModuleNavigator {
    fun navigateToUnpublishedNews()
    fun navigateToArticle(article: Article, isPreviewMode: Boolean = false)
    fun navigateToCreateArticle()
}