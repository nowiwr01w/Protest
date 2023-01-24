package com.nowiwr01p.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.auth.navigation.AuthScreenType
import com.nowiwr01p.auth.ui.auth.AuthMainScreen
import com.nowiwr01p.auth.ui.cities.CitiesMainScreen
import com.nowiwr01p.auth.ui.splash_screen.SplashScreen
import com.nowiwr01p.auth.ui.verification.VerificationMainScreen
import com.nowiwr01p.core_ui.Keys.ARG_TO_CITIES
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

    object CitiesMainScreen: AuthScreen<Boolean>(AuthScreenType.CitiesMainScreen.route, rootRoute) {
        override fun navigate(args: Boolean, navController: NavController) {
            navController.navigate(
                route = route.replace("{$ARG_TO_CITIES}", args.toString())
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(ARG_TO_CITIES) { type = NavType.BoolType }
                )
            ) {
                val fromProfile = it.arguments?.getBoolean(ARG_TO_CITIES) ?: false
                CitiesMainScreen(fromProfile, navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "auth"
    }
}