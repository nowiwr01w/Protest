package com.nowiwr01p.meetings.ui.main

import androidx.compose.runtime.mutableStateListOf
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.datastore.cities.data.Reaction
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.meetings.main.data.Story

interface MeetingsContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object OpenBecomeOrganizerLink: Event
        data class SelectStory(val story: Story): Event
        data class SelectCategory(val category: Category): Event
    }

    data class State(
        val showProgress: Boolean = false,
        val user: User = User(),
        val reactions: Map<String, Reaction> = mapOf(),
        val meetings: List<Meeting> = listOf(),
        val stories: List<Story> = mutableStateListOf(),
        val categories: List<Category> = mutableStateListOf(),
        val selectedStory: Story = Story(),
        val selectedCategory: Category = Category()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun toUnpublishedMeetings()
        fun toMeeting(meeting: Meeting)
        fun toProfile(editMode: Boolean)
        fun toCreateMeeting()
        fun openBecomeOrganizerLink()
        fun onStoryClick(story: Story)
        fun onCategoryClick(category: Category)
        fun toMapCurrentMeeting(meeting: Meeting)
    }
}