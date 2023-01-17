package com.nowiwr01p.news.ui.create_article

import androidx.compose.runtime.mutableStateListOf
import com.nowiwr01p.core.model.Image
import com.nowiwr01p.core.model.OrderedList
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface CreateArticleContract {

    sealed interface Event: ViewEvent {
        object NavigateBack: Event
    }

    data class State(
        val image: String = "",
        val title: String = "",
        val description: List<String> = mutableStateListOf(""),
        val images: List<Image> = mutableStateListOf(),
        val orderedLists: List<OrderedList> = mutableStateListOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateBack: Effect
    }

    interface Listener {
        fun onBackClick()
    }
}