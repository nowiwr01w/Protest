package com.nowiwr01p.auth.navigation

import com.nowiwr01p.core_ui.Keys

enum class AuthScreenType(val route: String) {
    SplashScreen("splash_screen"),
    AuthMainScreen("auth_main_screen"),
    VerificationMainScreen("verification_main_screen"),
    CitiesMainScreen("cities_main_screen?chosen_country={${Keys.ARG_TO_CITIES}}")
}