package com.nowiwr01p.news.ui.create_article

import androidx.compose.runtime.mutableStateListOf
import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType
import com.nowiwr01p.news.ui.create_article.data.DynamicFields
import com.nowiwr01p.news.ui.create_article.data.StaticFields

interface CreateArticleContract {

    sealed interface Event: ViewEvent {
        object NavigateBack: Event

        data class OnAddRemoveImageClick(
            val item: ImageList,
            val commonIndex: Int,
            val addOperation: Boolean
        ): Event

        data class OnAddStepItemClick(
            val item: OrderedList,
            val commonIndex: Int
        ): Event

        data class OnRemoveStepItemClick(
            val item: OrderedList,
            val commonIndex: Int,
            val removeIndex: Int
        ): Event

        data class OnBottomSheetTypeClick(
            val type: CreateArticleBottomSheetType
        ): Event

        data class ShowBottomSheet(
            val params: BottomSheetParams
        ): Event

        data class OnStaticFieldChanged(
            val type: StaticFields,
            val value: String
        ): Event

        data class OnDynamicFieldChanged(
            val contentItemIndex: Int,
            val insideItemIndex: Int,
            val type: DynamicFields,
            val value: String
        ): Event
    }

    data class State(
        val image: TopImage = TopImage(),
        val title: Title = Title(),
        val description: Description = Description(),
        val content: List<ArticleData> = mutableStateListOf(image, title, description)
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateBack: Effect
        object OnItemAdded: Effect
    }

    interface Listener {
        fun onBackClick()
        fun showBottomSheet()
        fun onStaticFieldChanged(type: StaticFields, value: String)
        fun onAddRemoveImageClick(item: ImageList, commonIndex: Int, addOperation: Boolean)
        fun onAddStepItemClick(item: OrderedList, commonIndex: Int)
        fun onRemoveStepItemClick(
            item: OrderedList,
            commonIndex: Int,
            removeIndex: Int
        )
        fun onDynamicFieldChanged(
            contentItemIndex: Int,
            insideItemIndex: Int,
            type: DynamicFields,
            value: String
        )
    }
}