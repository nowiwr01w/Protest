package com.nowiwr01p.domain.create_article.validators

import com.nowiwr01p.core.model.*
import com.nowiwr01p.domain.create_article.validators.data.CreateArticleError

interface CreateArticleValidator {
    fun validate(article: Article): List<CreateArticleError>
    fun validateTopImageLink(link: String)
    fun validateTitle(title: String)
    fun validateDescription(description: String)
    fun validateSubTitle(subTitle: SubTitle)
    fun validateText(text: Text)
    fun validateImageList(imageList: ImageList)
    fun validateOrderedList(orderedList: OrderedList)
}