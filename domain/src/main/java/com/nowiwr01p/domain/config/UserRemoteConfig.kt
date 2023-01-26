package com.nowiwr01p.domain.config

import kotlinx.coroutines.flow.StateFlow

interface UserRemoteConfig {
    fun isWriteNewsEverybodyActivated(): StateFlow<Boolean>
    fun isCreateMeetingEverybodyActivated(): StateFlow<Boolean>
}