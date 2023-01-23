package com.nowiwr01p.profile.di

import com.nowiwr01p.core_ui.navigators.ProfileNavigator
import com.nowiwr01p.profile.navigation.ProfileNavigatorImpl
import com.nowiwr01p.profile.ui.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleProfile = module {

    single<ProfileNavigator> {
        ProfileNavigatorImpl()
    }

    /**
     * PROFILE
     */
    viewModel { ProfileViewModel(get(), get(), get(), get(), get(), get()) }
}