package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.CreateArticleViewModel.ChangeImageType.*
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType.*
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType.SUBTITLE
import com.nowiwr01p.news.ui.create_article.data.DynamicFields
import com.nowiwr01p.news.ui.create_article.data.DynamicFields.*
import com.nowiwr01p.news.ui.create_article.data.StaticFields
import com.nowiwr01p.news.ui.create_article.data.StaticFields.*

class CreateArticleViewModel(
    private val showBottomSheetHelper: ShowBottomSheetHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.NavigateBack -> setEffect { Effect.NavigateBack }
            is Event.ShowBottomSheet -> showBottomSheet(event.params)
            is Event.OnBottomSheetTypeClick -> addField(event.type)
            is Event.OnStaticFieldChanged -> changeStaticField(event.type, event.value)
            is Event.OnDynamicFieldChanged -> changeDynamicField(event.index, event.subIndex, event.type, event.value)
        }
    }

    private fun showBottomSheet(params: BottomSheetParams) {
        showBottomSheetHelper.showBottomSheet(params)
    }

    /**
     * ADD CUSTOM TEXT FIELD ON THE SCREEN
     */
    private fun addField(type: CreateArticleBottomSheetType) = with(viewState.value) {
        val item = when (type) {
            SUBTITLE -> SubTitle(subTitles.size).also {
                setState { copy(subTitles = update(subTitles, it)) }
            }
            TEXT -> Description(descriptions.size).also {
                setState { copy(descriptions = update(descriptions, it)) }
            }
            IMAGE -> ImageList(images.size).also {
                setState { copy(images = update(images, it)) }
            }
            ORDERED_LIST -> OrderedList(orderedLists.size).also {
                setState { copy(orderedLists = update(orderedLists, it)) }
            }
        }
        setState { copy(content = update(content, item)) }
    }

    private inline fun <reified T: ArticleData> update(list: List<T>, value: T) = list
        .toMutableList()
        .apply { add(value) }


    /**
     * CHANGE TOP-3 ITEMS VALUES
     */
    private fun changeStaticField(type: StaticFields, value: String) = setState {
        when (type) {
            TOP_IMAGE_FIELD -> copy(image = TopImage(link = value))
            TITLE_FIELD -> copy(title = Title(text = value))
            DESCRIPTION_FIELD -> copy(description = Description(text = value))
        }
    }

    /**
     * CHANGE DYNAMIC FIELDS
     */
    private fun changeDynamicField(
        itemIndex: Int,
        subItemIndex: Int,
        type: DynamicFields,
        value: String
    ) = setState {
        when (type) {
            SUBTITLE_FIELD -> copy(subTitles = changeSubTitle(itemIndex, value))
            IMAGE_LINK -> copy(images = changeImage(itemIndex, subItemIndex, value, LINK))
            IMAGE_DETAILS -> copy(images = changeImage(itemIndex, subItemIndex, value, DETAILS))
            ORDERED_LIST_TITLE -> copy(orderedLists = changeOrderedListTitle(itemIndex, value))
            ORDERED_LIST_STEP -> copy(orderedLists = changeOrderedListStep(itemIndex, subItemIndex, value))
        }
    }

    /**
     * CHANGE SUBTITLE
     */
    private fun changeSubTitle(index: Int, value: String) = viewState.value
        .subTitles
        .toMutableList()
        .apply {
            this[index] = get(index).copy(text = value)
        }

    /**
     * CHANGE IMAGES
     */
    private enum class ChangeImageType {
        LINK, DETAILS
    }

    private fun changeImage(index: Int, subItemIndex: Int, value: String, type: ChangeImageType) =
        viewState.value.images.toMutableList().apply {
            this[index] = get(index).copy(
                images = get(index).images.toMutableList().apply {
                    this[subItemIndex] = if (type == LINK) {
                        this[subItemIndex].copy(link = value)
                    } else {
                        this[subItemIndex].copy(description = value)
                    }
                }
            )
        }

    /**
     * CHANGE ORDERED LIST
     */
    private fun changeOrderedListTitle(index: Int, value: String) = viewState.value
        .orderedLists
        .toMutableList()
        .apply {
            this[index] = get(index).copy(title = value)
        }

    private fun changeOrderedListStep(index: Int, subItemIndex: Int, value: String) = viewState.value
        .orderedLists.toMutableList().apply {
            this[index] = get(index).copy(
                steps = get(index).steps.toMutableList().apply {
                    this[subItemIndex] = value
                }
            )
        }
}