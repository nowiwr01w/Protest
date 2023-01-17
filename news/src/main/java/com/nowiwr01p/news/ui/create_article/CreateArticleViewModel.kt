package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.data.AddFieldType

class CreateArticleViewModel(
    private val showBottomSheetHelper: ShowBottomSheetHelper
): BaseViewModel<Event, State, Effect>() {

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

    private fun addField(type: AddFieldType) {
        // TODO
    }
}