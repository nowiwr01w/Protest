package com.nowiwr01p.meetings.ui.main

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.ui.status_bar.StatusBarColorHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.categories.usecase.GetCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.main.data.Story
import com.nowiwr01p.domain.meetings.main.usecase.*
import com.nowiwr01p.domain.meetings.main.usecase.data.MeetingsScreenCacheData
import com.nowiwr01p.domain.stories.usecase.GetStoriesUseCase
import com.nowiwr01p.domain.stories.usecase.GetStoriesViewersUseCase
import com.nowiwr01p.domain.user.usecase.GetUserUseCase
import com.nowiwr01p.meetings.ui.main.MeetingsContract.*
import com.nowiwr01p.meetings.ui.main.story_bottom_sheet.StoryBottomSheet
import kotlinx.coroutines.launch

class MeetingsViewModel(
    private val statusBarColor: Color,
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getStoriesViewersUseCase: GetStoriesViewersUseCase,
    private val getMeetingsUseCase: GetMeetingsUseCase,
    private val getReactionsUseCase: GetReactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getMeetingsScreenCacheUseCase: GetMeetingsScreenCacheUseCase,
    private val saveMeetingsScreenCacheUseCase: SaveMeetingsScreenCacheUseCase,
    private val setStoryViewedUseCase: SetStoryViewedUseCase,
    private val statusBarColorHelper: StatusBarColorHelper,
    private val showBottomSheetHelper: ShowBottomSheetHelper,
    private val openLinksHelper: OpenLinksHelper,
    private val mapper: MeetingsMapper
): BaseViewModel<Event, State, Effect>() {

    internal var allMeetings = listOf<Meeting>()
    internal var storiesViewers = mapOf<String, List<String>>()

    init {
        mapper.viewModel = this
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.SelectStory -> selectStory(event.story)
            is Event.SelectCategory -> selectCategory(event.category)
            is Event.OpenBecomeOrganizerLink -> openLink()
        }
    }

    private fun init() = io {
        setState { copy(showProgress = true) }
        runCatching {
            setStatusBarColor()
            getScreenCache()
            getUserData()
            getStoriesViewers()
            getStories()
            getReactions()
            getMeetings()
            getCategories()
        }.onSuccess {
            saveScreenCache()
            setState { copy(showProgress = false) }
        }
    }

    private fun setStatusBarColor() {
        statusBarColorHelper.setStatusBarColor(statusBarColor)
    }

    /**
     * USER DATA
     */
    private suspend fun getUserData() = launch {
        getUserUseCase.execute().collect { user ->
            setState { copy(user = user) }
        }
    }

    private suspend fun getReactions() = launch {
        getReactionsUseCase.execute().collect { meetingIdToReaction ->
            setState { copy(reactions = meetingIdToReaction) }
        }
    }

    /**
     * MEETINGS
     */
    private suspend fun getMeetings() = launch {
        getMeetingsUseCase.execute().collect { meetings ->
            allMeetings = meetings
            val filtered = mapper.updateMeetings(viewState.value.selectedCategory)
            setState { copy(meetings = filtered) }
        }
    }

    /**
     * STORIES VIEWERS
     */
    private suspend fun getStoriesViewers() = launch {
        getStoriesViewersUseCase.execute().collect { storyIdToViewers ->
            storiesViewers = storyIdToViewers
            val updatedStories = mapper.updateStories(storyIdToViewers)
            setState { copy(stories = updatedStories) }
        }
    }

    /**
     * STORIES
     */
    private suspend fun getStories() = launch {
        getStoriesUseCase.execute().collect { stories ->
            val updatedStories = mapper.updateStories(stories)
            setState { copy(stories = updatedStories) }
        }
    }

    /**
     * CATEGORIES
     */
    private suspend fun getCategories() {
        val categories = getCategoriesUseCase.execute().value
        setState { copy(categories = categories) }
    }

    /**
     * SCREEN CACHE
     */
    private suspend fun getScreenCache() = getMeetingsScreenCacheUseCase.execute()?.let { cache ->
        setState {
            copy(
                user = cache.data.user,
                stories = cache.data.stories,
                meetings = cache.data.meetings,
                categories = cache.data.categories,
                showProgress = false
            )
        }
    }

    private suspend fun saveScreenCache() = with(viewState.value) {
        val data = MeetingsScreenCacheData(
            user = user,
            stories = stories,
            meetings = meetings,
            categories = categories
        )
        saveMeetingsScreenCacheUseCase.execute(data)
    }

    /**
     * SELECT STORY
     */
    private fun selectStory(story: Story) {
        setStoryViewed(story.id)

        val params = BottomSheetParams(
            content = StoryBottomSheet(story),
            topPadding = 56.dp
        )
        showBottomSheetHelper.showBottomSheet(params)
    }

    /**
     * SELECT CATEGORY
     */
    private fun selectCategory(category: Category) = io {
        setState {
            copy(
                meetings = mapper.updateMeetings(category),
                categories = mapper.updateCategories(category),
                selectedCategory = mapper.updateSelectedCategory(category)
            )
        }
        saveScreenCache()
    }

    /**
     * SET STORY VIEWED
     */
    private fun setStoryViewed(id: String) = io {
        runCatching {
            setStoryViewedUseCase.execute(id)
        }
    }

    /**
     * SHOW BECOME ORGANIZER FORM
     */
    private fun openLink() = io {
        openLinksHelper.openLink("https://docs.google.com/forms/d/e/1FAIpQLScM9w8tzcwRRRGsvkVTRZqrcIc6zLzBmkDiKKvNe8oPOn7Nvw/viewform?usp=sharing")
    }
}