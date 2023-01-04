package com.nowiwr01p.navigation.start_screen.data

import com.nowiwr01p.auth.AuthScreen
import com.nowiwr01p.map.MapScreen

enum class StartScreenType(val roure: String) {
    AUTH(AuthScreen.AuthMainScreen.route),
    VERIFICATION(AuthScreen.VerificationMainScreen.route),
    COUNTRIES(AuthScreen.CountriesMainScreen.route),
    MAP(MapScreen.MapMainScreen.route)
}