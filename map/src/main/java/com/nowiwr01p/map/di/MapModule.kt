package com.nowiwr01p.map.di

import com.nowiwr01p.core_ui.navigators.MapNavigator
import com.nowiwr01p.domain.meetingsScreenScopeId
import com.nowiwr01p.map.navigation.MapNavigatorImpl
import com.nowiwr01p.map.ui.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMap = module {
    single<MapNavigator> {
        MapNavigatorImpl()
    }

    viewModel {
        val scope = getKoin().getScope(meetingsScreenScopeId)

        MapViewModel(
            get(),
            scope.get()
        )
    }
}