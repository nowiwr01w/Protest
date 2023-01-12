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
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingMainScreen
import com.nowiwr01p.meetings.ui.main.MeetingsMainScreen
import com.nowiwr01p.meetings.ui.create_meeting.map_draw_path.CurrentMeetingMapScreen
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
                    oldValue = "{${Keys.ARG_TO_MEETING_INFO}}",
                    newValue = Json.encodeToString(args)
                )
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(Keys.ARG_TO_MEETING_INFO) { type = NavType.StringType }
                )
            ) {
                val meetingJson = it.arguments?.getString(Keys.ARG_TO_MEETING_INFO).orEmpty()
                val meeting = Json.decodeFromString<Meeting>(meetingJson)
                MeetingMainScreen(meeting, navigator)
            }
        }
    }

    /**
     * CREATE MEETING
     */
    object CreateMeetingScreen: MeetingsScreen<Unit>(
        MeetingsScreenType.CreateMeetingMainScreen.route, rootRoute, false
    ) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                CreateMeetingMainScreen(navigator)
            }
        }
    }

    /**
     * MAP DRAW PATH
     */
    object MapDrawPathScreen: MeetingsScreen<Unit>(
        MeetingsScreenType.MapDrawPathScreen.route, rootRoute, false
    ) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                CurrentMeetingMapScreen(navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "meetings"
    }
}