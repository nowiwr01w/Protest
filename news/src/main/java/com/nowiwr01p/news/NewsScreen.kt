package com.nowiwr01p.news

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.Keys.ARG_ARTICLE
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.news.navigation.NewsScreenType
import com.nowiwr01p.news.ui.article.ArticleScreen
import com.nowiwr01p.news.ui.create_article.CreateArticleMainScreen
import com.nowiwr01p.news.ui.news.NewsMainScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class NewsScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = true
): Screen<T>() {

    /**
     * NEWS LIST
     */
    object NewsMainScreen: NewsScreen<Unit>(NewsScreenType.NewsScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigateOrPopup(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                NewsMainScreen(navigator)
            }
        }
    }

    /**
     * CREATE ARTICLE
     */
    object CreateArticleScreen: NewsScreen<Unit>(NewsScreenType.CreateArticleScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                CreateArticleMainScreen()
            }
        }
    }

    /**
     * ARTICLE
     */
    object ArticleScreen: NewsScreen<Article>(
        NewsScreenType.ArticleScreen.route, rootRoute, false
    ) {
        override fun navigate(args: Article, navController: NavController) {
            navController.navigate(
                route.replace("{$ARG_ARTICLE}", Json.encodeToString(args))
            )
        }

        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(ARG_ARTICLE) { type = NavType.StringType }
                )
            ) {
                val articleJson = it.arguments?.getString(ARG_ARTICLE).orEmpty()
                val article = Json.decodeFromString<Article>(articleJson)
                ArticleScreen(article, navigator)
            }
        }

    }
    companion object {
        const val rootRoute = "news"
    }
}