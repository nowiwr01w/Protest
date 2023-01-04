package com.nowiwr01p.navigation.di

import com.nowiwr01p.auth.di.moduleAuth
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.data.di.moduleData
import com.nowiwr01p.data.di.moduleCore
import com.nowiwr01p.domain.di.moduleDomain
import com.nowiwr01p.map.di.moduleMap
import com.nowiwr01p.meetings.di.moduleMeetings
import com.nowiwr01p.navigation.NavigatorImpl
import com.nowiwr01p.navigation.start_screen.ChooseStartScreenViewModel
import com.nowiwr01p.news.di.moduleNews
import com.nowiwr01p.profile.di.moduleProfile
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleNavigation = module {
    single<Navigator> {
        NavigatorImpl(get(), get(), get(), get(), get())
    }

    viewModel { ChooseStartScreenViewModel(get(), get(), get(), get()) }
}

val uiModules = listOf(
    moduleNavigation,
    moduleAuth,
    moduleMap,
    moduleMeetings,
    moduleNews,
    moduleProfile
)

val domainModules = listOf(
    moduleDomain
)

val dataModules = listOf(
    moduleData
)

val dataStoreModules = listOf(
    moduleCore
)

val appModules = uiModules + domainModules + dataModules + dataStoreModules