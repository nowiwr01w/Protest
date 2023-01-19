package com.nowiwr01p.news.ui.create_article.data

import com.nowiwr01p.core.model.ImageList
import com.nowiwr01p.core.model.OrderedList
import com.nowiwr01p.core.model.SubTitle
import com.nowiwr01p.core.model.Text
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.data.DynamicFields.*
import com.nowiwr01p.news.ui.create_article.data.StaticFields.*

open class CreateArticleDataType(
    open val type: CreateArticleFieldType,
    open val value: String = "",
    open val hint: String = "",
    open val showSubItemSlash: Boolean = false,
    open val onValueChanged: (item: String) -> Unit = { },
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
     * TEXT
     */
    data class TextItem(
        val state: State,
        val listener: Listener?,
        val commonIndex: Int,
        override val type: DynamicFields = TEXT_FIELD,
        override val value: String = (state.content[commonIndex] as Text).text,
        override val hint: String = "Текст",
        override val onValueChanged: (String) -> Unit = {
            listener?.onDynamicFieldChanged(commonIndex,-1, type, it)
        }
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
        val commonIndex: Int,
        override val type: DynamicFields = SUBTITLE_FIELD,
        override val value: String = (state.content[commonIndex] as SubTitle).text,
        override val hint: String = "Подзаголовок",
        override val onValueChanged: (String) -> Unit = {
            listener?.onDynamicFieldChanged(commonIndex, -1, type, it)
        }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        onValueChanged = onValueChanged
    )

    /**
     * IMAGE LIST
     */
    data class ImageLinkItem(
        val state: State,
        val listener: Listener?,
        val commonIndex: Int,
        val imageIndex: Int,
        override val type: DynamicFields = IMAGE_LINK,
        override val value: String = (state.content[commonIndex] as ImageList).images[imageIndex].link,
        override val hint: String = "Ссылка на картинку",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = {
            listener?.onDynamicFieldChanged(commonIndex, imageIndex, type, it)
        }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged
    )

    data class ImageDescriptionItem(
        val state: State,
        val listener: Listener?,
        val commonIndex: Int,
        val imageIndex: Int,
        override val type: DynamicFields = IMAGE_DETAILS,
        override val value: String = (state.content[commonIndex] as ImageList).images[imageIndex].description,
        override val hint: String = "Подпись (необязательно)",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = {
            listener?.onDynamicFieldChanged(commonIndex, imageIndex, type, it)
        }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged
    )

    /**
     * ORDERED LIST
     */
    data class ItemOrderedListTitle(
        val state: State,
        val listener: Listener?,
        val commonIndex: Int,
        override val type: DynamicFields = ORDERED_LIST_TITLE,
        override val value: String = (state.content[commonIndex] as OrderedList).title,
        override val hint: String = "Заголовок листа",
        override val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = {
            listener?.onDynamicFieldChanged(commonIndex, -1, type, it)
        }
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged
    )

    data class ItemOrderedListItem(
        val state: State,
        val listener: Listener?,
        val commonIndex: Int,
        val stepIndex: Int,
        override val type: DynamicFields = ORDERED_LIST_STEP,
        override val value: String = (state.content[commonIndex] as OrderedList).steps[stepIndex],
        override val hint: String = "Элемент листа",
        override val showSubItemSlash: Boolean = true,
        override val trailingIconCallback: (() -> Unit)? = null,
        override val onValueChanged: (String) -> Unit = {
            listener?.onDynamicFieldChanged(commonIndex, stepIndex, type, it)
        },
    ): CreateArticleDataType(
        type = type,
        value = value,
        hint = hint,
        showSubItemSlash = showSubItemSlash,
        onValueChanged = onValueChanged,
        trailingIconCallback = trailingIconCallback
    )
}