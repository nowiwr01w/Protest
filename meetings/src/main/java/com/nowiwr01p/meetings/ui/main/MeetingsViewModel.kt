package com.nowiwr01p.meetings.ui.main

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.status_bar.StatusBarColorHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.data.Story
import com.nowiwr01p.domain.meetings.usecase.*
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCacheData
import com.nowiwr01p.meetings.ui.main.MeetingsContract.*
import com.nowiwr01p.meetings.ui.main.story_bottom_sheet.StoryBottomSheet

class MeetingsViewModel(
    private val statusBarColor: Color,
    private val statusBarColorHelper: StatusBarColorHelper,
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getMeetingsUseCase: GetMeetingsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val getMeetingsScreenCacheUseCase: GetMeetingsScreenCacheUseCase,
    private val saveMeetingsScreenCacheUseCase: SaveMeetingsScreenCacheUseCase,
    private val showBottomSheetHelper: ShowBottomSheetHelper,
    private val mapper: MeetingsMapper
): BaseViewModel<Event, State, Effect>() {

    internal var allMeetings = listOf<Meeting>()

    init {
        mapper.viewModel = this
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.SelectStory -> selectStory(event.story)
            is Event.SelectCategory -> selectCategory(event.category)
        }
    }

    private fun init() = io {
        setState { copy(showProgress = true) }
        runCatching {
            setStatusBarColor()
            getScreenCache()
            getUserData()
            getStories()
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
     * LOCAL USER DATA
     */
    private suspend fun getUserData() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    /**
     * MEETINGS
     */
    private suspend fun getMeetings() = with(viewState.value) {
        getMeetingsUseCase.execute().also { allMeetings = it }
        val filtered = mapper.updateMeetings(selectedCategory)
        setState { copy(meetings = filtered) }
    }

    /**
     * STORIES
     */
    private suspend fun getStories() = getStoriesUseCase.execute().let { stories ->
        setState { copy(stories = stories) }
    }

    /**
     * CATEGORIES
     */
    private suspend fun getCategories() {
        val categories = getCategoriesUseCase.execute()
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
}