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
            is Event.OnAddImageClick -> addImage(event.item, event.commonIndex, event.innerIndex)
            is Event.OnAddStepItemClick -> addStepItem(event.item, event.commonIndex, event.innerIndex)
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
            TEXT -> Text(texts.size).also {
                setState { copy(texts = update(texts, it)) }
            }
            IMAGE -> ImageList(images.size).also {
                setState { copy(images = update(images, it)) }
            }
            ORDERED_LIST -> OrderedList(orderedLists.size).also {
                setState { copy(orderedLists = update(orderedLists, it)) }
            }
        }
        showBottomSheetHelper.closeBottomSheet(100)
        setState { copy(content = update(content, item)) }
        setEffect { Effect.ScrollDown }
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
            TEXT_FIELD -> copy(texts = changeText(itemIndex, value))
            SUBTITLE_FIELD -> copy(subTitles = changeSubTitle(itemIndex, value))
            IMAGE_LINK -> copy(images = changeImage(itemIndex, subItemIndex, value, LINK))
            IMAGE_DETAILS -> copy(images = changeImage(itemIndex, subItemIndex, value, DETAILS))
            ORDERED_LIST_TITLE -> copy(orderedLists = changeOrderedListTitle(itemIndex, value))
            ORDERED_LIST_STEP -> copy(orderedLists = changeOrderedListStep(itemIndex, subItemIndex, value))
        }
    }

    /**
     * CHANGE TEXT
     */
    private fun changeText(index: Int, value: String) = viewState.value
        .texts
        .toMutableList()
        .apply {
            this[index] = get(index).copy(text = value)
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

    /**
     * ADD DYNAMIC IMAGE FIELD
     */
    private fun addImage(item: ImageList, commonIndex: Int, innerIndex: Int) = with(viewState.value) {
        val updatedImages = item.images.toMutableList().apply {
            add(Image())
        }
        val updatedCommon = content.toMutableList().apply {
            this[commonIndex] = item.copy(images = updatedImages)
        }
        val updatedInner = images.toMutableList().apply {
            this[innerIndex] = item.copy(images = updatedImages)
        }
        setState { copy(images = updatedInner, content = updatedCommon) }
    }

    /**
     * ADD DYNAMIC ORDERED STEM ITEM FIELD
     */
    private fun addStepItem(item: OrderedList, commonIndex: Int, innerIndex: Int) = with(viewState.value) {
        val updatedSteps = item.steps.toMutableList().apply {
            add("")
        }
        val updatedCommon = content.toMutableList().apply {
            this[commonIndex] = item.copy(steps = updatedSteps)
        }
        val updatedInner = orderedLists.toMutableList().apply {
            this[innerIndex] = item.copy(steps = updatedSteps)
        }
        setState { copy(orderedLists = updatedInner, content = updatedCommon) }
    }
}