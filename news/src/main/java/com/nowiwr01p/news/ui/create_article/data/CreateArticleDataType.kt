package com.nowiwr01p.news.ui.create_article.data

import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.data.DynamicFields.*
import com.nowiwr01p.news.ui.create_article.data.StaticFields.*

open class CreateArticleDataType(
    open val type: CreateArticleFieldType,
    open val value: String = "",
    open val hint: String = "",
    open val order: Int = 0,
    open val showSubItemSlash: Boolean = false,
    open val onValueChanged: (String) -> Unit = { },
    open val trailingIconCallback: (() -> Unit)? = null
) {
    /**
     * TOP IMAGE
     */
    data class TopImageItem(
        val state: State,
        val listener: Listener?,
        override val type: StaticFields = TOP_IMAGE_FIELD,
        override val value: String = state.image.link,
        override val hint: String = "Ссылка на картинку",
        override val onValueChanged: (String) -> Unit = { listener?.onStaticFieldChanged(type, it) }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        onValueChanged = onValueChanged
    )

    /**
     * TITLE
     */
    data class TitleItem(
        val state: State,
        val listener: Listener?,
        override val type: StaticFields = TITLE_FIELD,
        override val value: String = state.title.text,
        override val hint: String = "Заголовок",
        override val onValueChanged: (String) -> Unit = { listener?.onStaticFieldChanged(type, it) }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        onValueChanged = onValueChanged
    )

    /**
     * DESCRIPTION
     */
    data class DescriptionItem(
        val state: State,
        val listener: Listener?,
        override val type: StaticFields = DESCRIPTION_FIELD,
        override val value: String = state.description.text,
        override val hint: String = "Описание",
        override val onValueChanged: (String) -> Unit = { listener?.onStaticFieldChanged(type, it) }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        onValueChanged = onValueChanged
    )

    /**
     * SUBTITLE
     */
    data class SubTitleItem(
        val state: State,
        val listener: Listener?,
        override val order: Int,
        override val type: DynamicFields = SUBTITLE_FIELD,
        override val value: String = state.subTitles[order].text,
        override val hint: String = "Подзаголовок",
        override val onValueChanged: (String) -> Unit = { listener?.onDynamicFieldChanged(order, -1, type, it) }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        order = order,
        onValueChanged = onValueChanged
    )

    /**
     * IMAGE LIST
     */
    data class ImageLinkItem(
        val state: State,
        val listener: Listener?,
        override val type: DynamicFields = IMAGE_LINK,
        override val order: Int,
        val imageIndex: Int,
        override val value: String = state.images[order].images[imageIndex].link,
        override val hint: String = "Ссылка на картинку",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onDynamicFieldChanged(order, imageIndex, type, it) }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        order = order,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged
    )

    data class ImageDescriptionItem(
        val state: State,
        val listener: Listener?,
        override val type: DynamicFields = IMAGE_DETAILS,
        override val order: Int,
        val imageIndex: Int,
        override val value: String = state.images[order].images[imageIndex].description,
        override val hint: String = "Подпись",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onDynamicFieldChanged(order, imageIndex, type, it) },
        override val trailingIconCallback: () -> Unit = { }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        order = order,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged,
        trailingIconCallback = trailingIconCallback
    )

    /**
     * ORDERED LIST
     */
    data class ItemOrderedListTitle(
        val state: State,
        val listener: Listener?,
        override val type: DynamicFields = ORDERED_LIST_TITLE,
        override val order: Int,
        override val value: String = state.orderedLists[order].title,
        override val hint: String = "Заголовок листа",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onDynamicFieldChanged(order, -1, type, it) }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        order = order,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged
    )

    data class ItemOrderedListItem(
        val state: State,
        val listener: Listener?,
        val stepIndex: Int,
        override val type: DynamicFields = ORDERED_LIST_STEP,
        override val order: Int,
        override val value: String = state.orderedLists[order].steps[stepIndex],
        override val hint: String = "Элемент листа",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onDynamicFieldChanged(order, stepIndex, type, it) },
        override val trailingIconCallback: (() -> Unit)? = null
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        order = order,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged,
        trailingIconCallback = if (stepIndex > 1) trailingIconCallback else null
    )
}