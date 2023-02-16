package com.nowiwr01p.news

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.core.extenstion.decodeObjectFromString
import com.nowiwr01p.core.extenstion.setObjectArgToNavigationRoute
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.Keys.ARG_ARTICLE
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.news.NewsScreen.ArticleScreen.Args
import com.nowiwr01p.news.navigation.NewsScreenType
import com.nowiwr01p.news.ui.article.ArticleMainScreen
import com.nowiwr01p.news.ui.create_article.CreateArticleMainScreen
import com.nowiwr01p.news.ui.news.NewsMainScreen
import com.nowiwr01p.news.ui.previews.UnpublishedNewsMainScreen
import kotlinx.serialization.Serializable

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
     * UNPUBLISHED NEWS LIST
     */
    object UnpublishedNewsMainScreen: NewsScreen<Unit>(
        NewsScreenType.UnpublishedNewsScreen.route,
        rootRoute,
        false
    ) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigateOrPopup(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                UnpublishedNewsMainScreen(navigator)
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
                CreateArticleMainScreen(navigator)
            }
        }
    }

    /**
     * ARTICLE
     */
    object ArticleScreen: NewsScreen<Args>(
        NewsScreenType.ArticleScreen.route, rootRoute, false
    ) {
        @Serializable
        data class Args(
            val article: Article,
            val isPreviewMode: Boolean,
            val isViewUnpublishedMode: Boolean
        )

        override fun navigate(args: Args, navController: NavController) {
            navController.navigate(
                route = route.setObjectArgToNavigationRoute(ARG_ARTICLE, args)
            )
        }

        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(ARG_ARTICLE) { type = NavType.StringType }
                )
            ) {
                val args = it.decodeObjectFromString<Args>(ARG_ARTICLE)
                ArticleMainScreen(
                    isPreviewMode = args.isPreviewMode,
                    isViewUnpublishedMode = args.isViewUnpublishedMode,
                    article = args.article,
                    navigator = navigator
                )
            }
        }

    }
    companion object {
        const val rootRoute = "news"
    }
}