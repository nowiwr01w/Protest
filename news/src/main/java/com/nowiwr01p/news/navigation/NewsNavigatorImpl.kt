package com.nowiwr01p.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.navigators.NewsNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.news.NewsScreen

class NewsNavigatorImpl: NewsNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun navigateToArticle(article: Article) {
        NewsScreen.NewsArticleScreen.navigate(article, navController)
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(NewsScreen.NewsMainScreen.route, NewsScreen.NewsMainScreen.rootRoute) {
            NewsScreen.NewsMainScreen.createScreen(builder, navigator)
            NewsScreen.NewsArticleScreen.createScreen(this, navigator)
        }
    }
}