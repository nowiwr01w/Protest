package com.nowiwr01p.domain.config

import kotlinx.coroutines.flow.StateFlow

interface CreateArticleRemoteConfig {
    fun getTextLength(): StateFlow<Int>
    fun getTitleLength(): StateFlow<Int>
}