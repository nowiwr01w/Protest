package com.nowiwr01p.core_ui.base_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nowiwr01p.core_ui.navigators.main.Navigator

abstract class Screen<T> {
    abstract val route: String
    abstract val rootRoute: String?
    abstract val showBottomNavigation: Boolean

    abstract fun navigate(args: T, navController: NavController)
    abstract fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator)

    protected fun navigateOrPopup(
        navController: NavController,
        route: String,
        navigateAction: () -> Unit
    ) {
        navController.currentBackStack.value
            .find { it.destination.route == route }
            ?.let { navController.popBackStack(route, inclusive = false, saveState = true) }
            ?: run { navigateAction() }
    }
}