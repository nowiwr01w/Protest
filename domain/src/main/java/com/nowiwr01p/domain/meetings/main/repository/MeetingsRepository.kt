package com.nowiwr01p.domain.meetings.main.repository

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.meetings.main.data.Story

interface MeetingsRepository {
    suspend fun getStories(): List<Story>
    suspend fun getMeetings(): List<Meeting>
    suspend fun getCategories(): List<Category>
    suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean): Meeting
    suspend fun setStoryViewed(storyId: String): Story
    suspend fun createMeeting(meeting: Meeting)
}