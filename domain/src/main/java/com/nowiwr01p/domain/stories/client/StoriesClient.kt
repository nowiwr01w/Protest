package com.nowiwr01p.domain.stories.client

import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.meetings.main.data.Story
import kotlinx.coroutines.flow.StateFlow

interface StoriesClient {
    suspend fun subscribeStories(): EventListener
    suspend fun getStories(): StateFlow<List<Story>>
    suspend fun setStoryViewed(storyId: String): Story
}