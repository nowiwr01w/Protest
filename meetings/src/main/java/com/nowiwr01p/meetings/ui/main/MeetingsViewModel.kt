package com.nowiwr01p.meetings.ui.main

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.usecase.*
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCacheData
import com.nowiwr01p.meetings.ui.main.MeetingsContract.*

class MeetingsViewModel(
    private val getMeetingsUseCase: GetMeetingsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val getMeetingsScreenCacheUseCase: GetMeetingsScreenCacheUseCase,
    private val saveMeetingsScreenCacheUseCase: SaveMeetingsScreenCacheUseCase,
    private val mapper: MeetingsMapper
): BaseViewModel<Event, State, Effect>() {

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
            getScreenCache()
            getUserData()
            getMeetings()
            getCategories()
        }.onSuccess {
            saveScreenCache()
            setState { copy(showProgress = false) }
        }
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
    private suspend fun getMeetings() {
        val meetings = getMeetingsUseCase.execute()
        setState { copy(meetings = meetings) }
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
    private fun selectCategory(category: Category) = with(viewState.value) {
        val updatedCategories = mapper.updateCategories(category)
        val updatedCategory = mapper.updateSelectedCategory(category)
        setState { copy(categories = updatedCategories, selectedCategory = updatedCategory) }
    }
}