package com.nowiwr01p.news.di

import com.nowiwr01p.core_ui.navigators.NewsNavigator
import com.nowiwr01p.news.navigation.NewsNavigatorImpl
import com.nowiwr01p.news.ui.NewsViewModel
import com.nowiwr01p.news.ui.news_article.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleNews = module {
    single<NewsNavigator> {
        NewsNavigatorImpl()
    }

    /**
     * NEWS
     */
    viewModel { NewsViewModel(get()) }

    /**
     * ARTICLE
     */
    viewModel { ArticleViewModel(get()) }
}