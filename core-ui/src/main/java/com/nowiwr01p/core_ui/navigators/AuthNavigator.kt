package com.nowiwr01p.core_ui.navigators

import com.nowiwr01p.core_ui.navigators.module.ModuleNavigator

interface AuthNavigator: ModuleNavigator {
    fun toAuth()
    fun toVerification()
    fun toChooseCountry()
    fun toChooseCity(countryCode: String)
}