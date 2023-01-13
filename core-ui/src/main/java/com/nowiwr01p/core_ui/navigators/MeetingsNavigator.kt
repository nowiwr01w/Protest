package com.nowiwr01p.core_ui.navigators

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.CreateMeetingMapType
import com.nowiwr01p.core_ui.navigators.module.ModuleNavigator

interface MeetingsNavigator: ModuleNavigator {
    fun navigateToMeetingInfo(meeting: Meeting)
    fun navigateToCreateMeeting()
    fun navigateToMapDrawPath(type: CreateMeetingMapType)
}