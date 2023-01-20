package com.nowiwr01p.news.ui.create_article

import androidx.compose.runtime.mutableStateListOf
import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.domain.create_article.validators.data.CreateArticleError
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType
import com.nowiwr01p.domain.create_article.validators.data.DynamicFields
import com.nowiwr01p.domain.create_article.validators.data.StaticFields

interface CreateArticleContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object NavigateBack: Event
        object NavigateToPreview: Event

        data class OnRemoveField(val commonIndex: Int): Event

        data class OnAddRemoveImageClick(
            val item: ImageList,
            val commonIndex: Int,
            val addOperation: Boolean
        ): Event

        data class OnAddRemoveStepItemClick(
            val item: OrderedList,
            val commonIndex: Int,
            val removeSubItemIndex: Int
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
        val userId: String = "",
        val image: TopImage = TopImage(),
        val title: Title = Title(),
        val description: Description = Description(),
        val content: List<ArticleData> = mutableStateListOf(image, title, description),
        val errors: List<CreateArticleError> = listOf()
    ): ViewState {

        fun isPreviewButtonVisible() = image.link.isNotEmpty()
                && title.text.isNotEmpty()
                && description.text.isNotEmpty()
    }

    sealed interface Effect: ViewSideEffect {
        object NavigateBack: Effect
        object OnItemAdded: Effect
        data class NavigateToPreview(val article: Article): Effect
    }

    interface Listener {
        fun onBackClick()
        fun onPreviewClick()
        fun showBottomSheet()
        fun onRemoveField(commonIndex: Int)
        fun onStaticFieldChanged(type: StaticFields, value: String)
        fun onAddRemoveImageClick(item: ImageList, commonIndex: Int, addOperation: Boolean)
        fun onAddRemoveStepItemClick(item: OrderedList, commonIndex: Int, removeIndex: Int = -1)
        fun onDynamicFieldChanged(
            contentItemIndex: Int,
            insideItemIndex: Int,
            type: DynamicFields,
            value: String
        )
    }
}