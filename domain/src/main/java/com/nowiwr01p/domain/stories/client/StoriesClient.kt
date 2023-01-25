package com.nowiwr01p.domain.stories.client

import com.nowiwr01p.domain.meetings.main.data.Story
import kotlinx.coroutines.flow.StateFlow

interface StoriesClient {
    suspend fun subscribeStories()
    suspend fun getStories(): StateFlow<List<Story>>
    suspend fun setStoryViewed(storyId: String): Story
}