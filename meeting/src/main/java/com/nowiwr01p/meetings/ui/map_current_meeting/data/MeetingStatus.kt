package com.nowiwr01p.meetings.ui.map_current_meeting.data

import com.nowiwr01p.core.datastore.cities.data.Meeting

enum class MeetingStatus(val type: String) {
    WAITING_FOR_PEOPLE("waiting"),
    IN_PROGRESS("running"),
    ENDED("ended");

    companion object {
        fun get(meeting: Meeting) = when {
            meeting.currentPosition.latitude != .0 -> IN_PROGRESS
            meeting.date - System.currentTimeMillis() <= 5 * 60 * 60 * 1000 -> WAITING_FOR_PEOPLE
            meeting.ended -> ENDED
            else -> throw IllegalStateException("Bro, you're completely wrong")
        }
    }
}