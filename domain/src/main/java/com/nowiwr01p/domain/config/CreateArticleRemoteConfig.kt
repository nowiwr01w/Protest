package com.nowiwr01p.domain.config

import kotlinx.coroutines.flow.StateFlow

interface CreateArticleRemoteConfig {
    fun getTextLength(): StateFlow<Int>
    fun getTitleLength(): StateFlow<Int>
    fun getOrderedListItemLength(): StateFlow<Int>
    fun getOrderedListTitleLength(): StateFlow<Int>
}