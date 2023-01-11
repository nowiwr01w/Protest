package com.nowiwr01p.meetings.navigation

import com.nowiwr01p.core_ui.Keys.ARG_TO_MEETING

enum class MeetingsScreenType(val route: String) {
    MeetingsMainScreen("meetings_main_screen"),
    MeetingMainScreen("meeting_main_screen?meeting={${ARG_TO_MEETING}}"),
    CreateMeetingMainScreen("create_meeting_main_screen"),
    MapAllMeetingsScreen("map_all_meetings_screen")
}