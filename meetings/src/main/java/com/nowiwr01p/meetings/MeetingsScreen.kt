package com.nowiwr01p.meetings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.Keys
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.meetings.navigation.MeetingsScreenType
import com.nowiwr01p.meetings.ui.main.MeetingsMainScreen
import com.nowiwr01p.meetings.ui.map_all_meetings.MapAllMeetingsScreen
import com.nowiwr01p.meetings.ui.meeting_info.MeetingMainScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class MeetingsScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = true
): Screen<T>() {

    /**
     * MEETINGS LIST
     */
    object MeetingsMainScreen: MeetingsScreen<Unit>(
        MeetingsScreenType.MeetingsMainScreen.route,
        rootRoute
    ) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigateOrPopup(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                MeetingsMainScreen(navigator)
            }
        }
    }

    /**
     * MEETING INFO
     */
    object MeetingMainScreen: MeetingsScreen<Meeting>(
        MeetingsScreenType.MeetingMainScreen.route,
        rootRoute,
        false
    ) {
        override fun navigate(args: Meeting, navController: NavController) {
            navController.navigate(
                route = route.replace(
                    oldValue = "{${Keys.ARG_TO_MEETING}}",
                    newValue = Json.encodeToString(args)
                )
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(Keys.ARG_TO_MEETING) { type = NavType.StringType }
                )
            ) {
                val meetingJson = it.arguments?.getString(Keys.ARG_TO_MEETING).orEmpty()
                val meeting = Json.decodeFromString<Meeting>(meetingJson)
                MeetingMainScreen(meeting, navigator)
            }
        }
    }

    /**
     * MAP ALL MEETINGS
     */
    object MapAllMeetingsScreen: MeetingsScreen<Unit>(
        MeetingsScreenType.MapAllMeetingsScreen.route,
        rootRoute,
        false
    ) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                MapAllMeetingsScreen(navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "meetings"
    }
}