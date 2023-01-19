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
            is Event.OnDynamicFieldChanged -> changeDynamicField(event.commonIndex, event.insideListIndex, event.insideItemIndex, event.type, event.value)
            is Event.OnAddImageClick -> addImage(event.item, event.commonIndex, event.innerIndex)
            is Event.OnAddStepItemClick -> addRemoveStepItem(event.item, event.commonIndex, event.innerIndex)
            is Event.OnRemoveStepItemClick -> addRemoveStepItem(event.item, event.commonIndex, event.innerIndex, event.removeIndex)
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
        setEffect { Effect.OnItemAdded }
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
        commonIndex: Int,
        insideListIndex: Int,
        insideItemIndex: Int,
        type: DynamicFields,
        value: String
    ) = with(viewState.value) {
        when (val updatedContentItem = content[commonIndex]) {
            is Text -> {
                val updatedContent = content.toMutableList().apply {
                    this[commonIndex] = updatedContentItem.copy(text = value)
                }
                setState { copy(texts = changeText(insideListIndex, value), content = updatedContent) }
            }
            is SubTitle -> {
                val updatedContent = content.toMutableList().apply {
                    this[commonIndex] = updatedContentItem.copy(text = value)
                }
                setState { copy(subTitles = changeSubTitle(insideListIndex, value), content = updatedContent) }
            }
            is ImageList -> {
                val imageType = if (type == IMAGE_LINK) LINK else DETAILS
                val updatedImages = changeImage(insideListIndex, insideItemIndex, value, imageType)
                val updatedContent = content.toMutableList().apply {
                    this[commonIndex] = updatedImages[insideListIndex]
                }
                setState { copy(images = updatedImages, content = updatedContent) }
            }
            is OrderedList -> if (type == ORDERED_LIST_TITLE) {
                val updatedOrderedLists = changeOrderedListTitle(insideListIndex, value)
                val updatedContent = content.toMutableList().apply {
                    this[commonIndex] = updatedOrderedLists[insideListIndex]
                }
                setState { copy(orderedLists = updatedOrderedLists, content = updatedContent) }
            } else {
                val updatedOrderedLists = changeOrderedListStep(insideListIndex, insideItemIndex, value)
                val updatedContent = content.toMutableList().apply {
                    this[commonIndex] = updatedOrderedLists[insideListIndex]
                }
                setState { copy(orderedLists = updatedOrderedLists, content = updatedContent) }
            }
            else -> throw IllegalStateException("Неверный тип элемента: ${updatedContentItem::class.java}")
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
     * ADD DYNAMIC ORDERED STEP ITEM FIELD
     */
    private fun addRemoveStepItem(
        item: OrderedList,
        commonIndex: Int,
        innerIndex: Int,
        removeIndex: Int = -1
    ) {
        with(viewState.value) {
            val updatedSteps = item.steps.toMutableList().apply {
                if (removeIndex == -1) add("") else removeAt(removeIndex)
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
}