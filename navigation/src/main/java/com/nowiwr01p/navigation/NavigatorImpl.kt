package com.nowiwr01p.navigation

import androidx.lifecycle.LiveData
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.nowiwr01p.auth.AuthScreen
import com.nowiwr01p.core_ui.Keys.ARG_TO_CITIES
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

    override fun navigateToProfile(editMode: Boolean) {
        ProfileScreen.ProfileMainScreen.navigate(editMode, navController)
    }

    override fun navigateToRoute(route: String) {
        // handle toCities() from splashScreen
        val resultRoute = route.replace("{$ARG_TO_CITIES}", "false")
        navController.navigate(resultRoute) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
        navController.graph.setStartDestination(resultRoute)
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
        AuthScreen.CitiesMainScreen.route -> AuthScreen.CitiesMainScreen
        MeetingsScreen.MeetingsMainScreen.route -> MeetingsScreen.MeetingsMainScreen
        MeetingsScreen.MeetingMainScreen.route -> MeetingsScreen.MeetingMainScreen
        MeetingsScreen.MeetingsPreviewMainScreen.route -> MeetingsScreen.MeetingsPreviewMainScreen
        MeetingsScreen.CreateMeetingScreen.route -> MeetingsScreen.CreateMeetingScreen
        MeetingsScreen.CreateMeetingMapScreen.route -> MeetingsScreen.CreateMeetingMapScreen
        NewsScreen.NewsMainScreen.route -> NewsScreen.NewsMainScreen
        ProfileScreen.ProfileMainScreen.route -> ProfileScreen.ProfileMainScreen
        else -> AuthScreen.AuthMainScreen
    }

    override fun <T> setScreenResult(key: String, result: T) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    override fun <T> getLiveDataResult(key: String): LiveData<T>? {
        return navController.currentBackStackEntry?.savedStateHandle?.getLiveData(key)
    }
}