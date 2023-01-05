package com.nowiwr01p.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.profile.navigation.ProfileScreenType
import com.nowiwr01p.profile.ui.ProfileMainScreen

sealed class ProfileScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = true
): Screen<T>() {

    object ProfileMainScreen: ProfileScreen<Unit>(ProfileScreenType.ProfileMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigateOrPopup(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                ProfileMainScreen()
            }
        }
    }

    companion object {
        const val rootRoute = "profile"
    }
}