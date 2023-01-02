package com.nowiwr01p.news.di

import com.nowiwr01p.core_ui.navigators.NewsNavigator
import com.nowiwr01p.news.navigation.NewsNavigatorImpl
import org.koin.dsl.module

val moduleNews = module {
    single<NewsNavigator> {
        NewsNavigatorImpl()
    }
}