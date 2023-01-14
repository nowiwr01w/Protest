package com.nowiwr01p.core_ui.navigators.main

import androidx.lifecycle.LiveData
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.bottom_navigation.BottomNavigationItem
import com.nowiwr01p.core_ui.navigators.*

interface Navigator {
    val authNavigator: AuthNavigator
    val meetingsNavigator: MeetingsNavigator
    val newsNavigator: NewsNavigator
    val profileNavigator: ProfileNavigator

    fun navigateUp()
    fun setGraphs(navGraphBuilder: NavGraphBuilder)

    fun getBottomNavigationItems(): List<BottomNavigationItem>
    fun onBottomNavigationSelected(item: BottomNavigationItem)

    fun navigateToMeetings()
    fun navigateToNews()
    fun navigateToProfile()

    fun navigateToRoute(route: String)
    fun setNavController(navHostController: NavHostController)

    fun currentScreen(): Screen<*>

    fun <T> setScreenResult(key: String, result: T)
    fun <T> getLiveDataResult(key: String): LiveData<T>?
}