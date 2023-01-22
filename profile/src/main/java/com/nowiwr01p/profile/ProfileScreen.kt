package com.nowiwr01p.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.core_ui.Keys.ARG_PROFILE_EDIT_MODE
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.profile.navigation.ProfileScreenType
import com.nowiwr01p.profile.ui.ProfileMainScreen

sealed class ProfileScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = true
): Screen<T>() {

    object ProfileMainScreen: ProfileScreen<Boolean>(ProfileScreenType.ProfileMainScreen.route, rootRoute) {
        override fun navigate(args: Boolean, navController: NavController) {
            navController.navigateOrPopup(
                route = route.replace("{${ARG_PROFILE_EDIT_MODE}}", args.toString())
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(ARG_PROFILE_EDIT_MODE) { type = NavType.BoolType }
                )
            ) {
                val editMode = it.arguments?.getBoolean(ARG_PROFILE_EDIT_MODE) ?: false
                ProfileMainScreen(editMode, navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "profile"
    }
}