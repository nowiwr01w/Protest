package com.nowiwr01p.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nowiwr01p.auth.navigation.AuthScreenType
import com.nowiwr01p.auth.ui.auth.AuthMainScreen
import com.nowiwr01p.auth.ui.cities.CitiesMainScreen
import com.nowiwr01p.auth.ui.splash_screen.SplashScreen
import com.nowiwr01p.auth.ui.verification.VerificationMainScreen
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator

sealed class AuthScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = false
): Screen<T>() {

    object SplashScreen: AuthScreen<Unit>(AuthScreenType.SplashScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                SplashScreen(navigator)
            }
        }
    }

    object AuthMainScreen: AuthScreen<Unit>(AuthScreenType.AuthMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigateAndMakeStart(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                AuthMainScreen(navigator)
            }
        }
    }

    object VerificationMainScreen: AuthScreen<Unit>(AuthScreenType.VerificationMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigateAndMakeStart(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                VerificationMainScreen(navigator)
            }
        }
    }

    object CitiesMainScreen: AuthScreen<Unit>(AuthScreenType.CitiesMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                CitiesMainScreen(navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "auth"
    }
}