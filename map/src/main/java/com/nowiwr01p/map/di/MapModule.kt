package com.nowiwr01p.map.di

import com.nowiwr01p.core_ui.navigators.MapNavigator
import com.nowiwr01p.map.navigation.MapNavigatorImpl
import org.koin.dsl.module

val moduleMap = module {
    single<MapNavigator> {
        MapNavigatorImpl()
    }
}