package com.nowiwr01p.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.auth.AuthScreen.CitiesMainScreen.Params
import com.nowiwr01p.auth.navigation.AuthScreenType
import com.nowiwr01p.auth.ui.auth.AuthMainScreen
import com.nowiwr01p.auth.ui.cities.CitiesMainScreen
import com.nowiwr01p.auth.ui.splash_screen.SplashScreen
import com.nowiwr01p.auth.ui.verification.VerificationMainScreen
import com.nowiwr01p.core_ui.Keys.ARG_TO_CITIES
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    object CitiesMainScreen: AuthScreen<Params>(AuthScreenType.CitiesMainScreen.route, rootRoute) {

        @Serializable
        data class Params(val fromProfile: Boolean, val fromCreateMeeting: Boolean)

        override fun navigate(args: Params, navController: NavController) {
            navController.navigate(
                route = route.replace("{$ARG_TO_CITIES}", Json.encodeToString(args))
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(ARG_TO_CITIES) { type = NavType.StringType }
                )
            ) {
                val paramsJson = it.arguments?.getString(ARG_TO_CITIES).orEmpty()
                val params = Json.decodeFromString<Params>(paramsJson)
                CitiesMainScreen(
                    fromProfile = params.fromProfile,
                    fromCreateMeeting = params.fromCreateMeeting,
                    navigator = navigator
                )
            }
        }
    }

    companion object {
        const val rootRoute = "auth"
    }
}