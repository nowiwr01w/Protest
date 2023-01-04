package com.nowiwr01p.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.auth.navigation.AuthScreenType
import com.nowiwr01p.auth.ui.auth.AuthMainScreen
import com.nowiwr01p.auth.ui.location.LocationMainScreen
import com.nowiwr01p.auth.ui.verification.VerificationMainScreen
import com.nowiwr01p.core_ui.Keys.ARG_TO_CITIES
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator

sealed class AuthScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = false
): Screen<T> {

    object AuthMainScreen: AuthScreen<Unit>(AuthScreenType.AuthMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                AuthMainScreen(navigator)
            }
        }
    }

    object VerificationMainScreen: AuthScreen<Unit>(AuthScreenType.VerificationMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
            navController.graph.setStartDestination(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                VerificationMainScreen(navigator)
            }
        }
    }

    object CountriesMainScreen: AuthScreen<Unit>(AuthScreenType.CountriesMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
            navController.graph.setStartDestination(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                LocationMainScreen("", navigator)
            }
        }
    }

    object CitiesMainScreen: AuthScreen<String>(AuthScreenType.CitiesMainScreen.route, rootRoute) {
        override fun navigate(args: String, navController: NavController) {
            navController.navigate(
                route.replace("{$ARG_TO_CITIES}", args)
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(ARG_TO_CITIES) { type = NavType.StringType }
                )
            ) {
                val countryCode = it.arguments?.getString(ARG_TO_CITIES).orEmpty()
                LocationMainScreen(countryCode, navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "auth"
    }
}