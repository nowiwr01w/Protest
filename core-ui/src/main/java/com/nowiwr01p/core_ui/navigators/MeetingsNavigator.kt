package com.nowiwr01p.core_ui.navigators

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.CreateMeetingMapType
import com.nowiwr01p.core_ui.navigators.module.ModuleNavigator

interface MeetingsNavigator: ModuleNavigator {
    fun navigateToMapCurrentMeeting(meetingId: String)
    fun navigateToMeetingInfo(isPreviewMode: Boolean, isViewUnpublishedMode: Boolean, meeting: Meeting)
    fun navigateToCreateMeeting()
    fun navigateToUnpublishedMeetings()
    fun navigateToMapDrawPath(type: CreateMeetingMapType)
}