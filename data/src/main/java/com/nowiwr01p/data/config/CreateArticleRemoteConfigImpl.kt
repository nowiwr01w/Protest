package com.nowiwr01p.data.config

import com.google.firebase.remoteconfig.ktx.get
import com.nowiwr01p.domain.config.CreateArticleRemoteConfig
import com.nowiwr01p.domain.config.RemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateArticleRemoteConfigImpl: RemoteConfig(), CreateArticleRemoteConfig {

    override fun initValues() {
        initTextLength()
        initTitleLength()
        initOrderedListItemLength()
        initOrderedListTitleLength()
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

    private companion object {
        const val TEXT_LENGTH = "article_text_length"
        const val TITLE_LENGTH = "article_title_length"
        const val ORDERED_LIST_ITEM_LENGTH = "article_ordered_item_length"
        const val ORDERED_LIST_TITLE_LENGTH = "article_ordered_title_length"
    }
}