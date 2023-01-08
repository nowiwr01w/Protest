package com.nowiwr01p.meetings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nowiwr01p.core_ui.base_screen.Screen
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.meetings.navigation.MeetingsScreenType
import com.nowiwr01p.meetings.ui.main.MeetingsMainScreen
import com.nowiwr01p.meetings.ui.meeting.MeetingMainScreen

sealed class MeetingsScreen<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute,
    override val showBottomNavigation: Boolean = true
): Screen<T>() {

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

    object MeetingMainScreen: MeetingsScreen<Unit>(
        MeetingsScreenType.MeetingMainScreen.route,
        rootRoute,
        false
    ) {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                MeetingMainScreen(navigator)
            }
        }
    }

    companion object {
        const val rootRoute = "meetings"
    }
}