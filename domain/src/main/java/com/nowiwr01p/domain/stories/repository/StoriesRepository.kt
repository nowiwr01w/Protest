package com.nowiwr01p.domain.stories.repository

import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.meetings.main.data.Story
import kotlinx.coroutines.flow.StateFlow

interface StoriesRepository {
    suspend fun subscribeStories(): ReferencedListener
    suspend fun getStories(): StateFlow<List<Story>>
    suspend fun setStoryViewed(storyId: String): Story
}