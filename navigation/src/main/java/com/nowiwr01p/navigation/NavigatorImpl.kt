package com.nowiwr01p.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.nowiwr01p.core_ui.bottom_navigation.BottomNavigationItem
import com.nowiwr01p.core_ui.navigators.*
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.map.MapScreen
import com.nowiwr01p.meetings.MeetingsScreen
import com.nowiwr01p.navigation.BottomNavigationItems.*
import com.nowiwr01p.news.NewsScreen
import com.nowiwr01p.profile.ProfileScreen

class NavigatorImpl(
    override val mapNavigator: MapNavigator,
    override val meetingsNavigator: MeetingsNavigator,
    override val newsNavigator: NewsNavigator,
    override val profileNavigator: ProfileNavigator
) : Navigator {

    private lateinit var navController: NavHostController

    private val navigators = listOf(
        mapNavigator, meetingsNavigator, newsNavigator, profileNavigator
    )

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun setGraphs(navGraphBuilder: NavGraphBuilder) {
        navigators.forEach {
            it.graph(navGraphBuilder, this@NavigatorImpl)
        }
    }

    override fun getBottomNavigationItems() = listOf(
        Map, Meetings, News, Profile
    )

    override fun onBottomNavigationSelected(item: BottomNavigationItem) {
        when (item) {
            Map -> navigateToMap()
            Meetings -> navigateToMeetings()
            News -> navigateToNews()
            Profile -> navigateToProfile()
        }
    }

    override fun navigateToMap() {
        MapScreen.MapMainScreen.navigate(Unit, navController)
    }

    override fun navigateToMeetings() {
        MeetingsScreen.MeetingsMainScreen.navigate(Unit, navController)
    }

    override fun navigateToNews() {
        NewsScreen.NewsMainScreen.navigate(Unit, navController)
    }

    override fun navigateToProfile() {
        ProfileScreen.ProfileMainScreen.navigate(Unit, navController)
    }

    override fun navigateToRoute(route: String) {
        navController.navigate(route)
    }

    override fun setNavController(navHostController: NavHostController) {
        navController = navHostController

        navigators.forEach {
            it.setNavController(navController)
        }
    }

    override fun currentScreen() = when (navController.currentDestination?.route) {
        MapScreen.MapMainScreen.route -> MapScreen.MapMainScreen
        MeetingsScreen.MeetingsMainScreen.route -> MeetingsScreen.MeetingsMainScreen
        NewsScreen.NewsMainScreen.route -> NewsScreen.NewsMainScreen
        ProfileScreen.ProfileMainScreen.route -> ProfileScreen.ProfileMainScreen
        else -> MapScreen.MapMainScreen
    }
}