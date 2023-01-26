package com.nowiwr01p.data.config

import com.google.firebase.remoteconfig.ktx.get
import com.nowiwr01p.domain.config.RemoteConfig
import com.nowiwr01p.domain.config.UserRemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserRemoteConfigImpl: RemoteConfig(), UserRemoteConfig {

    override fun initValues() {
        initIsWriteNewsEverybodyActivated()
        initIsCreateMeetingEverybodyActivated()
    }

    /**
     * WRITE NEWS EVERYBODY
     */
    private val _writeNewsEverybodyStateFlow = MutableStateFlow(false)
    override fun isWriteNewsEverybodyActivated() = _writeNewsEverybodyStateFlow.asStateFlow()

    private fun initIsWriteNewsEverybodyActivated() = _writeNewsEverybodyStateFlow.update {
        config[WRITE_NEWS_EVERYBODY].asBoolean()
    }

    /**
     * CREATE MEETINGS EVERYBODY
     */
    private val _createMeetingsEverybodyStateFlow = MutableStateFlow(false)
    override fun isCreateMeetingEverybodyActivated() = _createMeetingsEverybodyStateFlow.asStateFlow()

    private fun initIsCreateMeetingEverybodyActivated() = _createMeetingsEverybodyStateFlow.update {
        config[CREATE_MEETINGS_EVERYBODY].asBoolean()
    }

    private companion object {
        const val WRITE_NEWS_EVERYBODY = "write_news_everybody"
        const val CREATE_MEETINGS_EVERYBODY = "create_meetings_everybody"
    }
}