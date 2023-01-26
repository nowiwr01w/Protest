package com.nowiwr01p.domain.config

import kotlinx.coroutines.flow.StateFlow

interface RemoteConfig {
    fun getTextLength(): StateFlow<Int>
    fun getTitleLength(): StateFlow<Int>
    fun getOrderedListItemLength(): StateFlow<Int>
    fun getOrderedListTitleLength(): StateFlow<Int>
    fun isWriteNewsEverybodyActivated(): StateFlow<Boolean>
    fun isCreateMeetingEverybodyActivated(): StateFlow<Boolean>
}