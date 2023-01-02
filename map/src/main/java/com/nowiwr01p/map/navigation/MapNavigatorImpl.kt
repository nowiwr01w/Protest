package com.nowiwr01p.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.core_ui.navigators.MapNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.map.MapScreen
import com.nowiwr01p.map.MapScreen.*

class MapNavigatorImpl: MapNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(MapMainScreen.route, MapScreen.rootRoute) {
            MapMainScreen.createScreen(builder, navigator)
        }
    }
}