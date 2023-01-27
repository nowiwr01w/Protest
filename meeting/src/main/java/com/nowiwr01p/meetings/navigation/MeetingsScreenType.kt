package com.nowiwr01p.meetings.navigation

import com.nowiwr01p.core_ui.Keys.ARG_CREATE_MEETING_TO_MAP
import com.nowiwr01p.core_ui.Keys.ARG_TO_MEETING_INFO

enum class MeetingsScreenType(val route: String) {
    MeetingsMainScreen("meetings_main_screen"),
    MeetingMainScreen("meeting_main_screen?meeting={${ARG_TO_MEETING_INFO}}"),
    CreateMeetingMainScreen("create_meeting_main_screen"),
    CreateMeetingMapScreen("create_meeting_map_screen?=mapType={${ARG_CREATE_MEETING_TO_MAP}}")
}