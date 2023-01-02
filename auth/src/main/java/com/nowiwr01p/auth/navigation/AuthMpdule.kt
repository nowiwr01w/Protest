package com.nowiwr01p.auth.navigation

import com.nowiwr01p.core_ui.navigators.AuthNavigator
import org.koin.dsl.module

val moduleAuth = module {
    single<AuthNavigator> {
        AuthNavigatorImpl()
    }
}