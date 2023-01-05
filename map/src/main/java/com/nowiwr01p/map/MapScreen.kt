package com.nowiwr01p.map

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.map.navigation.MapScreenType
import com.nowiwr01p.map.ui.MapMainScreen

sealed class MapScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = true
): Screen<T>() {

    object MapMainScreen: MapScreen<Unit>(MapScreenType.MapMainScreen.route, rootRoute) {
        override fun navigate(args: Unit, navController: NavController) {
            with(navController) {
                navigateOrPopup(route) { navigateAndMakeStart(route) }
            }
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                MapMainScreen(navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "map"
    }
}