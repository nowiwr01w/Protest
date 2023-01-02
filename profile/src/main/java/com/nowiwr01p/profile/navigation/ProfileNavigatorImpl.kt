package com.nowiwr01p.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.core_ui.navigators.ProfileNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.profile.ProfileScreen

class ProfileNavigatorImpl: ProfileNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(ProfileScreen.ProfileMainScreen.route, ProfileScreen.ProfileMainScreen.rootRoute) {
            ProfileScreen.ProfileMainScreen.createScreen(builder, navigator)
        }
    }
}