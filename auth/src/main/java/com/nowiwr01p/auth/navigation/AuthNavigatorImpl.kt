package com.nowiwr01p.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.auth.AuthScreen.*
import com.nowiwr01p.core_ui.navigators.AuthNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator

class AuthNavigatorImpl: AuthNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun toAuth() {
        AuthMainScreen.navigate(Unit, navController)
    }

    override fun toVerification() {
        VerificationMainScreen.navigate(Unit, navController)
    }

    override fun toChooseCity() {
        CitiesMainScreen.navigate(Unit, navController)
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(SplashScreen.route, SplashScreen.rootRoute) {
            SplashScreen.createScreen(builder, navigator)
            AuthMainScreen.createScreen(this, navigator)
            VerificationMainScreen.createScreen(this, navigator)
            CitiesMainScreen.createScreen(this, navigator)
        }
    }
}