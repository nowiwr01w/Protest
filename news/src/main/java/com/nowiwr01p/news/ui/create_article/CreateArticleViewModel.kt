package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.data.AddFieldType
import com.nowiwr01p.news.ui.create_article.data.AddFieldType.*

class CreateArticleViewModel(
    private val showBottomSheetHelper: ShowBottomSheetHelper
): BaseViewModel<Event, State, Effect>() {

    private var order = 3

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.NavigateBack -> setEffect { Effect.NavigateBack }
            is Event.ShowBottomSheet -> showBottomSheet(event.params)
            is Event.OnBottomSheetTypeClick -> addField(event.type)
        }
    }

    private fun showBottomSheet(params: BottomSheetParams) {
        showBottomSheetHelper.showBottomSheet(params)
    }

    private fun addField(type: AddFieldType) = setState {
        when (type) {
            SUBTITLE -> {
                val item = SubTitle(order)
                copy(subTitles = update(subTitles, item))
            }
            TEXT -> {
                val item = Description(order)
                copy(descriptions = update(descriptions, item))
            }
            IMAGE -> {
                val item = ImageList(order)
                copy(images = update(images, item))
            }
            ORDERED_LIST -> {
                val item = OrderedList(order)
                copy(orderedLists = update(orderedLists, item))
            }
        }
    }.also {
        order += 1
    }

    private inline fun <reified T: ArticleData> update(list: List<T>, value: T) = list
        .toMutableList()
        .apply { add(value) }
}