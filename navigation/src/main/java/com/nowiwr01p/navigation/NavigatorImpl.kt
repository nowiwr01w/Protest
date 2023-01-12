package com.nowiwr01p.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.nowiwr01p.auth.AuthScreen
import com.nowiwr01p.core_ui.bottom_navigation.BottomNavigationItem
import com.nowiwr01p.core_ui.navigators.*
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.meetings.MeetingsScreen
import com.nowiwr01p.navigation.BottomNavigationItems.*
import com.nowiwr01p.news.NewsScreen
import com.nowiwr01p.profile.ProfileScreen

class NavigatorImpl(
    override val authNavigator: AuthNavigator,
    override val meetingsNavigator: MeetingsNavigator,
    override val newsNavigator: NewsNavigator,
    override val profileNavigator: ProfileNavigator
) : Navigator {

    private lateinit var navController: NavHostController

    private val navigators = listOf(
        authNavigator, meetingsNavigator, newsNavigator, profileNavigator
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
        Meetings, News, Profile
    )

    override fun onBottomNavigationSelected(item: BottomNavigationItem) {
        when (item) {
            Meetings -> navigateToMeetings()
            News -> navigateToNews()
            Profile -> navigateToProfile()
        }
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
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
        navController.graph.setStartDestination(route)
    }

    override fun setNavController(navHostController: NavHostController) {
        navController = navHostController

        navigators.forEach {
            it.setNavController(navController)
        }
    }

    override fun currentScreen() = when (navController.currentDestination?.route) {
        AuthScreen.SplashScreen.route -> AuthScreen.SplashScreen
        AuthScreen.AuthMainScreen.route -> AuthScreen.AuthMainScreen
        AuthScreen.VerificationMainScreen.route -> AuthScreen.VerificationMainScreen
        AuthScreen.CountriesMainScreen.route -> AuthScreen.CountriesMainScreen
        AuthScreen.CitiesMainScreen.route -> AuthScreen.CitiesMainScreen
        MeetingsScreen.MeetingsMainScreen.route -> MeetingsScreen.MeetingsMainScreen
        MeetingsScreen.MeetingMainScreen.route -> MeetingsScreen.MeetingMainScreen
        MeetingsScreen.CreateMeetingScreen.route -> MeetingsScreen.CreateMeetingScreen
        MeetingsScreen.MapDrawPathScreen.route -> MeetingsScreen.MapDrawPathScreen
        NewsScreen.NewsMainScreen.route -> NewsScreen.NewsMainScreen
        ProfileScreen.ProfileMainScreen.route -> ProfileScreen.ProfileMainScreen
        else -> AuthScreen.AuthMainScreen
    }
}