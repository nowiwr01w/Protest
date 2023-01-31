package com.nowiwr01p.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.navigators.NewsNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.news.NewsScreen
import com.nowiwr01p.news.NewsScreen.ArticleScreen

class NewsNavigatorImpl: NewsNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun navigateToUnpublishedNews() {
        NewsScreen.UnpublishedNewsMainScreen.navigate(Unit, navController)
    }

    override fun navigateToCreateArticle() {
        NewsScreen.CreateArticleScreen.navigate(Unit, navController)
    }

    override fun navigateToArticle(article: Article, isPreviewMode: Boolean, isViewUnpublishedMode: Boolean) {
        val args = ArticleScreen.Args(article, isPreviewMode, isViewUnpublishedMode)
        ArticleScreen.navigate(args, navController)
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(NewsScreen.NewsMainScreen.route, NewsScreen.NewsMainScreen.rootRoute) {
            NewsScreen.NewsMainScreen.createScreen(builder, navigator)
            NewsScreen.UnpublishedNewsMainScreen.createScreen(this, navigator)
            NewsScreen.CreateArticleScreen.createScreen(this, navigator)
            ArticleScreen.createScreen(this, navigator)
        }
    }
}