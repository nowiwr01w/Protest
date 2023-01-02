package com.nowiwr01p.profile.di

import com.nowiwr01p.core_ui.navigators.ProfileNavigator
import com.nowiwr01p.profile.navigation.ProfileNavigatorImpl
import org.koin.dsl.module

val moduleProfile = module {
    single<ProfileNavigator> {
        ProfileNavigatorImpl()
    }
}