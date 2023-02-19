package com.nowiwr01p.meetings.ui.main

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.mapper.ViewModelMapper
import com.nowiwr01p.domain.meetings.main.data.Story

class MeetingsMapper: ViewModelMapper<MeetingsViewModel>() {

    fun updateSelectedCategory(category: Category): Category {
        return if (category.isSelected) Category() else category
    }

    fun updateCategories(category: Category) = viewModel.viewState.value.categories.map {
        if (it.name == category.name) {
            it.copy(isSelected = !category.isSelected)
        } else {
            it.copy(isSelected = false)
        }
    }

    fun updateMeetings(category: Category) = with(viewModel.viewState.value) {
        viewModel.allMeetings
            .filter { meeting ->
                meeting.cityName == user.city.name || meeting.cityName == "everywhere"
            }
            .filter { meeting ->
            val selectedName = updateSelectedCategory(category).name
            val found = meeting.categories.find { category -> category.name == selectedName } != null
            found || selectedName.isEmpty()
        }
    }

    fun updateStories(viewers: Map<String, List<String>>) = with(viewModel.viewState.value) {
        stories.map { story ->
            val currentViewers = viewers[story.id].orEmpty()
            story.copy(viewers = currentViewers)
        }
    }
}