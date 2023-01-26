package com.nowiwr01p.data.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.config.RemoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RemoteConfigImpl(
    private val config: FirebaseRemoteConfig,
    private val dispatchers: AppDispatchers
): RemoteConfig {

    init {
        CoroutineScope(dispatchers.io).launch {
            initConfig()
        }
    }

    private suspend fun initConfig() = config.fetchAndActivate().await().let { activated ->
        if (activated) {
            initTextLength()
            initTitleLength()
            initOrderedListItemLength()
            initOrderedListTitleLength()
            initIsWriteNewsEverybodyActivated()
            initIsCreateMeetingEverybodyActivated()
        }
    }

    /**
     * TEXT LENGTH
     */
    private val _textLengthStateFlow = MutableStateFlow(450)
    override fun getTextLength() = _textLengthStateFlow.asStateFlow()

    private fun initTextLength() = _textLengthStateFlow.update {
        config[TEXT_LENGTH].asLong().toInt()
    }

    /**
     * TITLE LENGTH
     */
    private val _titleLengthStateFlow = MutableStateFlow(72)
    override fun getTitleLength() = _titleLengthStateFlow.asStateFlow()

    private fun initTitleLength() = _titleLengthStateFlow.update {
        config[TITLE_LENGTH].asLong().toInt()
    }

    /**
     * ORDERED LIST ITEM LENGTH
     */
    private val _orderedListItemLengthStateFlow = MutableStateFlow(72)
    override fun getOrderedListItemLength() = _orderedListItemLengthStateFlow.asStateFlow()

    private fun initOrderedListItemLength() = _orderedListItemLengthStateFlow.update {
        config[ORDERED_LIST_ITEM_LENGTH].asLong().toInt()
    }

    /**
     * ORDERED LIST TITLE LENGTH
     */
    private val _orderedListTitleLengthStateFlow = MutableStateFlow(72)
    override fun getOrderedListTitleLength() = _orderedListTitleLengthStateFlow.asStateFlow()

    private fun initOrderedListTitleLength() = _orderedListTitleLengthStateFlow.update {
        config[ORDERED_LIST_TITLE_LENGTH].asLong().toInt()
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
        const val TEXT_LENGTH = "text_length"
        const val TITLE_LENGTH = "title_length"
        const val ORDERED_LIST_ITEM_LENGTH = "ordered_list_item_length"
        const val ORDERED_LIST_TITLE_LENGTH = "ordered_list_title_length"
        const val WRITE_NEWS_EVERYBODY = "write_news_everybody"
        const val CREATE_MEETINGS_EVERYBODY = "create_meetings_everybody"
    }
}