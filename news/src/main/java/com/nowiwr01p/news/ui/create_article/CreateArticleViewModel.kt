package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType.*
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType.SUBTITLE
import com.nowiwr01p.news.ui.create_article.data.DynamicFields
import com.nowiwr01p.news.ui.create_article.data.DynamicFields.*
import com.nowiwr01p.news.ui.create_article.data.StaticFields
import com.nowiwr01p.news.ui.create_article.data.StaticFields.*
import java.util.*

class CreateArticleViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val showBottomSheetHelper: ShowBottomSheetHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.NavigateBack -> setEffect { Effect.NavigateBack }
            is Event.ShowBottomSheet -> showBottomSheet(event.params)
            is Event.OnBottomSheetTypeClick -> addField(event.type)
            is Event.OnRemoveField -> removeField(event.commonIndex)
            is Event.OnStaticFieldChanged -> changeStaticField(event.type, event.value)
            is Event.OnDynamicFieldChanged -> changeDynamicField(event.contentItemIndex, event.insideItemIndex, event.type, event.value)
            is Event.OnAddRemoveImageClick -> addRemoveImage(event.item, event.commonIndex, event.addOperation)
            is Event.OnAddRemoveStepItemClick -> addRemoveStepItem(event.item, event.commonIndex, event.removeSubItemIndex)
            is Event.NavigateToPreview -> toPreview()
        }
    }

    private fun init() = io {
        val userId = getLocalUserUseCase.execute().id
        setState { copy(userId = userId) }
    }

    private fun showBottomSheet(params: BottomSheetParams) {
        showBottomSheetHelper.showBottomSheet(params)
    }

    /**
     * ADD CUSTOM TEXT FIELD ON THE SCREEN
     */
    private fun addField(type: CreateArticleBottomSheetType) = with(viewState.value) {
        val item = when (type) {
            SUBTITLE -> SubTitle()
            TEXT -> Text()
            IMAGE -> ImageList()
            ORDERED_LIST -> OrderedList()
        }
        showBottomSheetHelper.closeBottomSheet(100)
        setState { copy(content = update(content, item)) }
        setEffect { Effect.OnItemAdded }
    }

    private inline fun <reified T: ArticleData> update(list: List<T>, value: T) = list
        .toMutableList()
        .apply { add(value) }


    /**
     * REMOVE CUSTOM TEXT FIELD ON THE SCREEN
     */
    private fun removeField(commonIndex: Int) = with(viewState.value) {
        val updated = content.toMutableList().apply {
            removeAt(commonIndex)
        }
        setState { copy(content = updated) }
    }

    /**
     * CHANGE TOP-3 ITEMS VALUES
     */
    private fun changeStaticField(type: StaticFields, value: String) = setState {
        when (type) {
            TOP_IMAGE_FIELD -> {
                val item = image.copy(link = value)
                val updatedContent =  content.toMutableList().apply {
                    this[0] = item
                }
                copy(image = item, content = updatedContent)
            }
            TITLE_FIELD -> {
                val item = title.copy(text = value)
                val updatedContent =  content.toMutableList().apply {
                    this[1] = item
                }
                copy(title = item, content = updatedContent)
            }
            DESCRIPTION_FIELD -> {
                val item = description.copy(text = value)
                val updatedContent =  content.toMutableList().apply {
                    this[2] = item
                }
                copy(description= item, content = updatedContent)
            }
        }
    }

    /**
     * CHANGE DYNAMIC FIELDS
     */
    private fun changeDynamicField(
        contentItemIndex: Int,
        insideItemIndex: Int,
        type: DynamicFields,
        value: String
    ) = with(viewState.value) {
        when (val updatedContentItem = content[contentItemIndex]) {
            /** CHANGE TEXT **/
            is Text -> {
                val updatedContent = content.toMutableList().apply {
                    this[contentItemIndex] = updatedContentItem.copy(text = value)
                }
                setState { copy(content = updatedContent) }
            }
            /** CHANGE SUBTITLE **/
            is SubTitle -> {
                val updatedContent = content.toMutableList().apply {
                    this[contentItemIndex] = updatedContentItem.copy(text = value)
                }
                setState { copy(content = updatedContent) }
            }
            /** CHANGE IMAGES **/
            is ImageList -> {
                val updatedImages = updatedContentItem.images.toMutableList().apply {
                    this[insideItemIndex] = get(insideItemIndex).let {
                        if (type == IMAGE_LINK) it.copy(link = value) else it.copy(description = value)
                    }
                }
                val updatedContent = content.toMutableList().apply {
                    this[contentItemIndex] = updatedContentItem.copy(images = updatedImages)
                }
                setState { copy(content = updatedContent) }
            }
            /** CHANGE ORDERED LIST **/
            is OrderedList -> if (type == ORDERED_LIST_TITLE) {
                val updatedOrderedList = updatedContentItem.copy(title = value)
                val updatedContent = content.toMutableList().apply {
                    this[contentItemIndex] = updatedOrderedList
                }
                setState { copy(content = updatedContent) }
            } else {
                val updatedSteps = updatedContentItem.steps.toMutableList().apply {
                    this[insideItemIndex] = value
                }
                val updatedOrderedLists = updatedContentItem.copy(steps = updatedSteps)
                val updatedContent = content.toMutableList().apply {
                    this[contentItemIndex] = updatedOrderedLists
                }
                setState { copy(content = updatedContent) }
            }
            else -> throw IllegalStateException("Неверный тип элемента: ${updatedContentItem::class.java}")
        }
    }

    /**
     * ADD DYNAMIC IMAGE FIELD
     */
    private fun addRemoveImage(item: ImageList, commonIndex: Int, add: Boolean) = with(viewState.value) {
        val updatedImages = item.images.toMutableList().apply {
            if (add) add(Image())
            if (!add && isNotEmpty()) removeLast()
        }
        val updatedCommon = content.toMutableList().apply {
            if (updatedImages.isEmpty()) {
                removeAt(commonIndex)
            } else {
                this[commonIndex] = item.copy(images = updatedImages)
            }
        }
        setState { copy(content = updatedCommon) }
    }

    /**
     * ADD DYNAMIC ORDERED STEP ITEM FIELD
     */
    private fun addRemoveStepItem(
        item: OrderedList,
        commonIndex: Int,
        removeSubItemIndex: Int = -1
    ) {
        with(viewState.value) {
            val updatedSteps = item.steps.toMutableList().apply {
                if (removeSubItemIndex == -1) add("") else removeAt(removeSubItemIndex)
            }
            val updatedCommon = content.toMutableList().apply {
                this[commonIndex] = item.copy(steps = updatedSteps)
            }
            setState { copy(content = updatedCommon) }
        }
    }

    /**
     * BUILD ARTICLE & NAVIGATE TO PREVIEW
     */

    private fun toPreview() = with(viewState.value) {
        val orderedContent = content.toMutableList().mapIndexed { index, articleData ->
            articleData.setItemOrder(index)
        }
        val dateViewers = DateViewers(
            date = System.currentTimeMillis(),
            viewers = listOf(userId)
        )
        with(orderedContent) {
            val article = Article(
                id = UUID.randomUUID().toString(),
                topImage = filterIsInstance<TopImage>().first(),
                dateViewers = dateViewers,
                title = filterIsInstance<Title>().first(),
                description = filterIsInstance<Description>().first(),
                text = filterIsInstance<Text>(),
                subtitles = filterIsInstance<SubTitle>(),
                imagesLists = filterIsInstance<ImageList>(),
                orderedLists = filterIsInstance<OrderedList>(),
            )
            setEffect { Effect.NavigateToPreview(article) }
        }
    }
}