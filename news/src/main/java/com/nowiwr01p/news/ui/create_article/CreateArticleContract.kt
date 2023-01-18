package com.nowiwr01p.news.ui.create_article

import androidx.compose.runtime.mutableStateListOf
import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.news.ui.create_article.data.CreateArticleFieldType

interface CreateArticleContract {

    sealed interface Event: ViewEvent {
        object NavigateBack: Event
        data class OnBottomSheetTypeClick(val type: CreateArticleFieldType): Event
        data class ShowBottomSheet(val params: BottomSheetParams): Event
    }

    data class State(
        val image: TopImage = TopImage(),
        val title: Title = Title(),
        val description: Description = Description(),
        val subTitles: List<SubTitle> = mutableStateListOf(),
        val descriptions: List<Description> = mutableStateListOf(),
        val images: List<ImageList> = mutableStateListOf(),
        val orderedLists: List<OrderedList> = mutableStateListOf(),
        val content: List<ArticleData> = mutableStateListOf(image, title, description)
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateBack: Effect
    }

    interface Listener {
        fun onBackClick()
        fun showBottomSheet()
    }
}