package com.nowiwr01p.domain.stories.repository

import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.meetings.main.data.Story
import kotlinx.coroutines.flow.StateFlow

interface StoriesRepository {
    suspend fun subscribeStories(): ReferencedListener
    suspend fun getStories(): StateFlow<List<Story>>
    suspend fun subscribeStoriesViewers(): ReferencedListener
    suspend fun getStoriesViewers(): StateFlow<Map<String, List<String>>>
    suspend fun setStoryViewed(storyId: String)
}