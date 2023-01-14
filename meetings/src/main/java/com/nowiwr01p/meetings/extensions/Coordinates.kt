package com.nowiwr01p.meetings.extensions

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.User

internal fun User.getCityCoordinates() = with(city) {
    LatLng(latitude, longitude)
}

internal fun Meeting.getMeetingCoordinates() = with (locationInfo.locationStartPoint) {
    LatLng(latitude, longitude)
}