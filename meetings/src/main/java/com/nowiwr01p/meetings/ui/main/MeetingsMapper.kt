package com.nowiwr01p.meetings.ui.main

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.mapper.ViewModelMapper

class MeetingsMapper: ViewModelMapper<MeetingsViewModel>() {

    fun updateCategories(category: Category) = viewModel.viewState.value.categories.map {
        if (it.name == category.name) {
            it.copy(isSelected = !category.isSelected)
        } else {
            it.copy(isSelected = false)
        }
    }

    fun updateSelectedCategory(category: Category) = if (category.isSelected) Category() else category
}