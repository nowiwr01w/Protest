package com.nowiwr01p.news.di

import com.nowiwr01p.core_ui.navigators.NewsNavigator
import com.nowiwr01p.domain.news.main.usecase.GetNewsScreenCacheUseCase
import com.nowiwr01p.domain.news.main.usecase.SaveNewsScreenCacheUseCase
import com.nowiwr01p.domain.news.main.usecase.data.NewsScreenCache
import com.nowiwr01p.news.navigation.NewsNavigatorImpl
import com.nowiwr01p.news.ui.news.NewsViewModel
import com.nowiwr01p.news.ui.article.ArticleViewModel
import com.nowiwr01p.news.ui.create_article.CreateArticleMapper
import com.nowiwr01p.news.ui.create_article.CreateArticleViewModel
import com.nowiwr01p.news.ui.previews.UnpublishedNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleNews = module {
    single<NewsNavigator> {
        NewsNavigatorImpl()
    }

    /**
     * NEWS
     */
    scope(named(newsScreenScopeName)) {
        scoped { NewsScreenCache() }
        scoped { GetNewsScreenCacheUseCase(get()) }
        scoped { SaveNewsScreenCacheUseCase(get()) }
    }

    viewModel {
        val scope = getKoin().getOrCreateScope(newsScreenScopeId, named(newsScreenScopeName))

        NewsViewModel(
            get(),
            scope.get(),
            scope.get(),
            get(),
            get()
        )
    }

    /**
     * UNPUBLISHED NEWS
     */
    viewModel {
        UnpublishedNewsViewModel(get())
    }

    /**
     * CREATE ARTICLE
     */
    factory {
        CreateArticleMapper()
    }

    viewModel {
        CreateArticleViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    /**
     * ARTICLE
     */
    viewModel {
        ArticleViewModel(
            get(),
            get(),
            get()
        )
    }
}

private const val newsScreenScopeId = "newsScreenScopeId"
private const val newsScreenScopeName = "newsScreenScopeName"