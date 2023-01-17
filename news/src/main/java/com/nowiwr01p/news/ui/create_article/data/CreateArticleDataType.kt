package com.nowiwr01p.news.ui.create_article.data

import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*

sealed class CreateArticleDataType(
    open val value: String = "",
    open val hint: String = "",
    open val index: Int = 0,
    open val showSubItemSlash: Boolean = false,
    open val onValueChanged: (String) -> Unit = { },
    open val trailingIconCallback: (() -> Unit)? = null
) {

    data class TopImage(
        val state: State,
        override val value: String = state.image,
        override val hint: String = "Ссылка на картинку"
    ): CreateArticleDataType(value, hint)

    data class Title(
        val state: State,
        override val value: String = state.title,
        override val hint: String = "Заголовок"
    ): CreateArticleDataType(value, hint)

    data class Description(
        val state: State,
        override val index: Int,
        override val value: String = state.description[index],
        override val hint: String = "Текст"
    ): CreateArticleDataType(value, hint, index)

    data class ImageLink(
        val state: State,
        override val index: Int,
        override val value: String = state.images[index].link,
        override val hint: String = "Ссылка на картинку"
    ): CreateArticleDataType(value, hint, index)

    data class ImageDescription(
        val state: State,
        override val index: Int,
        override val value: String = state.images[index].description,
        override val hint: String = "Подпись"
    ): CreateArticleDataType(value, hint, index)

    data class OrderedListTitle(
        val state: State,
        override val index: Int,
        override val value: String = state.orderedLists[index].title,
        override val hint: String = "Заголовок"
    ): CreateArticleDataType(value, hint, index)

    data class OrderedListItem(
        val state: State,
        val stepIndex: Int,
        override val index: Int,
        override val value: String = state.orderedLists[index].steps[stepIndex],
        override val hint: String = "Заголовок"
    ): CreateArticleDataType(value, hint, index)
}