package com.nowiwr01p.meetings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.navigators.MeetingsNavigator
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.meetings.MeetingsScreen.*

class MeetingsNavigatorImpl: MeetingsNavigator {

    private lateinit var navController: NavController

    override fun setNavController(curNavController: NavController) {
        navController = curNavController
    }

    override fun navigateToMeetingInfo(meeting: Meeting) {
        MeetingMainScreen.navigate(meeting, navController)
    }

    override fun navigateToCurrentMeetingMap(meeting: Meeting) {
        MapDrawPathScreen.navigate(meeting, navController)
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(MeetingsMainScreen.route, MeetingsMainScreen.rootRoute) {
            MeetingsMainScreen.createScreen(builder, navigator)
            MapDrawPathScreen.createScreen(this, navigator)
            MeetingMainScreen.createScreen(this, navigator)
        }
    }
}