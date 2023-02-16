package com.nowiwr01p.data.news.create_article.validator

import com.nowiwr01p.core.model.*
import com.nowiwr01p.domain.config.CreateArticleRemoteConfig
import com.nowiwr01p.domain.news.create_article.validators.CreateArticleValidator
import com.nowiwr01p.domain.news.create_article.validators.data.CreateArticleError
import com.nowiwr01p.domain.news.create_article.validators.data.CreateArticleError.*

class CreateArticleValidatorImpl(config: CreateArticleRemoteConfig): CreateArticleValidator {

    private val errors = mutableListOf<CreateArticleError?>()

    private val textLength = config.getTextLength()
    private val titleLength = config.getTitleLength()

    override fun validate(article: Article) = article.buildContent().forEach { item ->
        when (item) {
            is TopImage -> validateTopImageLink(item.link)
            is Title -> validateTitle(item.text)
            is Description -> validateDescription(item.text)
            is Text -> validateText(item)
            is SubTitle -> validateSubTitle(item)
            is ImageList -> validateImageList(item)
            is OrderedList -> validateOrderedList(item)
        }
    }.let {
        errors.distinct().filterNotNull()
    }.also {
        errors.clear()
    }

    /**
     * TOP IMAGE
     */
    override fun validateTopImageLink(link: String) {
        validateImage(
            link = link,
            extensionError = TopImageError.ExtensionError()
        )
    }

    /**
     * TITLE
     */
    override fun validateTitle(title: String) {
        when {
            title.isEmpty() ->TitleError.EmptyTitleError()
            title.length > titleLength.value -> TitleError.LongTitleError(titleLength.value)
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * DESCRIPTION
     */
    override fun validateDescription(description: String) {
        when {
            description.isEmpty() -> DescriptionError.EmptyDescriptionError()
            description.length > textLength.value -> DescriptionError.LongDescriptionError(textLength.value)
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * SUBTITLE
     */
    override fun validateSubTitle(subTitle: SubTitle) {
        when {
            subTitle.text.isEmpty() -> SubTitleError.EmptySubTitleError(
                contentIndex = subTitle.order
            )
            subTitle.text.length > titleLength.value -> SubTitleError.LongSubTitleError(
                titleLength = titleLength.value,
                contentIndex = subTitle.order
            )
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * TEXT
     */
    override fun validateText(text: Text) {
        when {
            text.text.isEmpty() -> TextError.EmptyTextError(
                contentIndex = text.order
            )
            text.text.length > textLength.value -> TextError.LongTextError(
                textLength = textLength.value,
                contentIndex = text.order
            )
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * IMAGE LIST
     */
    override fun validateImageList(imageList: ImageList) = imageList.images.forEach { image ->
        validateImage(
            link = image.link,
            extensionError = ImageListError.ExtensionError(contentIndex = imageList.order)
        )
    }

    /**
     * ORDERED LIST
     */
    override fun validateOrderedList(orderedList: OrderedList): Unit = with(orderedList) {
        when {
            orderedList.title.isEmpty() -> OrderedListError.EmptyTitleError(contentIndex = order)
            orderedList.steps.any { it.trim().isEmpty() } -> OrderedListError.EmptyStepItemError(contentIndex = order)
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * VALIDATE IMAGE
     */
    private fun validateImage(
        link: String,
        extensionError: CreateArticleError,
    ) = with(link) {
        val ext = startsWith("https://")
        when {
            !ext -> extensionError
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * ADD ERROR
     */
    private fun addError(error: CreateArticleError?) {
        errors.add(error)
    }
}