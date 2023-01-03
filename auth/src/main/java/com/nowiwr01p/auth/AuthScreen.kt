package com.nowiwr01p.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nowiwr01p.auth.navigation.AuthScreenType
import com.nowiwr01p.auth.ui.auth.AuthMainScreen
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

    companion object {
        const val rootRoute = "auth"
    }
}