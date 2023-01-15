package com.nowiwr01p.auth.di

import com.nowiwr01p.auth.navigation.AuthNavigatorImpl
import com.nowiwr01p.auth.ui.auth.AuthViewModel
import com.nowiwr01p.auth.ui.cities.CitiesMapper
import com.nowiwr01p.auth.ui.cities.CitiesViewModel
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

    viewModel { AuthViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    /**
     * VERIFICATION
     */
    viewModel { VerificationViewModel(get(), get(), get()) }

    /**
     * LOCATION
     */
    factory {
        CitiesMapper()
    }

    viewModel { CitiesViewModel(get(), get(), get()) }
}