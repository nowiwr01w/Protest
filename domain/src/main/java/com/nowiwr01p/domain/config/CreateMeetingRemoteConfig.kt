package com.nowiwr01p.domain.config

import kotlinx.coroutines.flow.StateFlow

interface CreateMeetingRemoteConfig {
    fun getTextLength(): StateFlow<Int>
    fun getTitleLength(): StateFlow<Int>
    fun getPathDotsCount(): StateFlow<Int>
    fun getHoursDifference(): StateFlow<Int>
    fun getLocationPlaceLength(): StateFlow<Int>
    fun getLocationDetailsLength(): StateFlow<Int>
}