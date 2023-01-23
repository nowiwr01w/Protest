package com.nowiwr01p.data.news.create_article.validator

import com.nowiwr01p.core.model.*
import com.nowiwr01p.domain.news.create_article.validators.CreateArticleValidator
import com.nowiwr01p.domain.news.create_article.validators.data.CreateArticleError
import com.nowiwr01p.domain.news.create_article.validators.data.CreateArticleError.*

class CreateArticleValidatorImpl: CreateArticleValidator {

    private val errors = mutableListOf<CreateArticleError?>()

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
            extensionError = TopImageError.ExtensionError(),
            imageTypeError = TopImageError.ImageTypeError()
        )
    }

    /**
     * TITLE
     */
    override fun validateTitle(title: String) {
        when {
            title.isEmpty() ->TitleError.EmptyTitleError()
            title.length > 48 -> TitleError.LongTitleError()
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
            description.length > 300 -> DescriptionError.LongDescriptionError()
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
            subTitle.text.isEmpty() -> SubTitleError.EmptySubTitleError(contentIndex = subTitle.order)
            subTitle.text.length > 48 -> SubTitleError.LongSubTitleError(contentIndex = subTitle.order)
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
            text.text.isEmpty() -> TextError.EmptyTextError(contentIndex = text.order)
            text.text.length > 48 -> TextError.LongTextError(contentIndex = text.order)
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
            extensionError = ImageListError.ExtensionError(contentIndex = imageList.order),
            imageTypeError = ImageListError.ImageTypeError(contentIndex = imageList.order)
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
        imageTypeError: CreateArticleError
    ) = with(link) {
        val ext = startsWith("https://")
        val image = endsWith(".png") || endsWith(".jpg") || endsWith(".jpeg")
        when {
            !ext -> extensionError
            !image -> imageTypeError
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