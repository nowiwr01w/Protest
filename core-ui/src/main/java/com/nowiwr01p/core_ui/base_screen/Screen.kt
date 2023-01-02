package com.nowiwr01p.core_ui.base_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nowiwr01p.core_ui.navigators.main.Navigator

interface Screen<T> {
    val route: String
    val rootRoute: String?
    val showBottomNavigation: Boolean

    fun navigate(args: T, navController: NavController)
    fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator)
}