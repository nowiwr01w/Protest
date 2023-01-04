package com.nowiwr01p.auth.di

import com.nowiwr01p.auth.navigation.AuthNavigatorImpl
import com.nowiwr01p.auth.ui.auth.AuthViewModel
import com.nowiwr01p.auth.ui.location.LocationMapper
import com.nowiwr01p.auth.ui.location.LocationViewModel
import com.nowiwr01p.auth.ui.verification.VerificationViewModel
import com.nowiwr01p.core_ui.navigators.AuthNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleAuth = module {

    /***
     * AUTH
     */
    single<AuthNavigator> {
        AuthNavigatorImpl()
    }

    viewModel { AuthViewModel(get(), get(), get(), get(), get(), get()) }

    /**
     * VERIFICATION
     */
    viewModel { VerificationViewModel(get(), get()) }

    /**
     * LOCATION
     */
    factory {
        LocationMapper()
    }

    viewModel { LocationViewModel(get(), get(), get(), get(), get()) }
}