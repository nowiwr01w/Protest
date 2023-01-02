package com.nowiwr01p.navigation.di

import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.map.di.moduleMap
import com.nowiwr01p.meetings.di.moduleMeetings
import com.nowiwr01p.navigation.NavigatorImpl
import com.nowiwr01p.news.di.moduleNews
import com.nowiwr01p.profile.di.moduleProfile
import org.koin.dsl.module

val moduleNavigation = module {
    single<Navigator> {
        NavigatorImpl(get(), get(), get(), get())
    }
}

val appModules = listOf(
    moduleNavigation,
    moduleMap,
    moduleMeetings,
    moduleNews,
    moduleProfile
)