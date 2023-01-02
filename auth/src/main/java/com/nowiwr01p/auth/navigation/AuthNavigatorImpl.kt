package com.nowiwr01p.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.auth.AuthScreen
import com.nowiwr01p.auth.AuthScreen.*
import com.nowiwr01p.core_ui.navigators.AuthNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator

class AuthNavigatorImpl: AuthNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(AuthMainScreen.route, AuthScreen.rootRoute) {
            AuthMainScreen.createScreen(builder, navigator)
        }
    }
}