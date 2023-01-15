package com.nowiwr01p.meetings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.CreateMeetingMapType
import com.nowiwr01p.core_ui.Keys
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.meetings.navigation.MeetingsScreenType
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingMainScreen
import com.nowiwr01p.meetings.ui.main.MeetingsMainScreen
import com.nowiwr01p.meetings.ui.create_meeting.map.CurrentMeetingMapScreen
import com.nowiwr01p.meetings.ui.meeting_info.MeetingMainScreen
import kotlinx.serialization.Serializable
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
            navController.navigateOrPopup(route) { navController.navigateAndMakeStart(route) }
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
    object MeetingMainScreen: MeetingsScreen<MeetingMainScreen.Args>(
        MeetingsScreenType.MeetingMainScreen.route,
        rootRoute,
        false
    ) {
        @Serializable
        data class Args(
            val isPreviewMode: Boolean,
            val meeting: Meeting
        )

        override fun navigate(args: Args, navController: NavController) {
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
                val args = Json.decodeFromString<Args>(meetingJson)

                MeetingMainScreen(
                    isPreviewMode = args.isPreviewMode,
                    meeting = args.meeting,
                    navigator = navigator
                )
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
            navController.navigate(route) {
                popUpTo(route) {
                    saveState = true
                }
                restoreState = true
            }
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
    object CreateMeetingMapScreen: MeetingsScreen<CreateMeetingMapType>(
        MeetingsScreenType.CreateMeetingMapScreen.route, rootRoute, false
    ) {
        override fun navigate(args: CreateMeetingMapType, navController: NavController) {
            navController.navigate(
                route.replace("{${Keys.ARG_CREATE_MEETING_TO_MAP}}", args.type)
            )
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route = route,
                arguments = listOf(
                    navArgument(Keys.ARG_CREATE_MEETING_TO_MAP) { type = NavType.StringType }
                )
            ) {
                val type = it.arguments!!.getString(Keys.ARG_CREATE_MEETING_TO_MAP)!!.let { type ->
                    CreateMeetingMapType.findByType(type)
                }
                CurrentMeetingMapScreen(type, navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "meetings"
    }
}