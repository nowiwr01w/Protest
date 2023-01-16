package com.nowiwr01p.meetings.ui.main

import androidx.compose.ui.graphics.Color
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.ui.status_bar.StatusBarColorHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.usecase.*
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCacheData
import com.nowiwr01p.meetings.ui.main.MeetingsContract.*

class MeetingsViewModel(
    private val statusBarColor: Color,
    private val statusBarColorHelper: StatusBarColorHelper,
    private val getMeetingsUseCase: GetMeetingsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val getMeetingsScreenCacheUseCase: GetMeetingsScreenCacheUseCase,
    private val saveMeetingsScreenCacheUseCase: SaveMeetingsScreenCacheUseCase,
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
            is Event.SelectCategory -> selectCategory(event.category)
            is Event.ShowBottomSheet -> { } // TODO
        }
    }

    private fun init() = io {
        setState { copy(showProgress = true) }
        runCatching {
            setStatusBarColor()
            getScreenCache()
            getUserData()
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
                meetings = cache.data.meetings,
                categories = cache.data.categories,
                showProgress = false
            )
        }
    }

    private suspend fun saveScreenCache() = with(viewState.value) {
        val data = MeetingsScreenCacheData(
            user = user,
            meetings = meetings,
            categories = categories
        )
        saveMeetingsScreenCacheUseCase.execute(data)
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