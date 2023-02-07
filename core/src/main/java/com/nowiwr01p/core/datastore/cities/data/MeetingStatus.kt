package com.nowiwr01p.core.datastore.cities.data

enum class MeetingStatus(val type: String) {
    WAITING_FOR_PEOPLE("waiting"),
    IN_PROGRESS("running"),
    ENDED("ended")
}