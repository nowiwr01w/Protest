package com.nowiwr01p.core_ui.navigators

import com.nowiwr01p.core_ui.navigators.module.ModuleNavigator

interface AuthNavigator: ModuleNavigator {
    fun toAuth()
    fun toVerification()
    fun toChooseCity(fromProfile: Boolean = false, fromCreateMeeting: Boolean = false)
}