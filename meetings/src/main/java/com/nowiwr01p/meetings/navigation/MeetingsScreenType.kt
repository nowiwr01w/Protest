package com.nowiwr01p.meetings.navigation

import com.nowiwr01p.core_ui.Keys.ARG_TO_MEETING_INFO

enum class MeetingsScreenType(val route: String) {
    MeetingsMainScreen("meetings_main_screen"),
    MeetingMainScreen("meeting_main_screen?meeting={${ARG_TO_MEETING_INFO}}"),
    CreateMeetingMainScreen("create_meeting_main_screen"),
    MapDrawPathScreen("map_draw_path_screen}")
}