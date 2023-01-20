package com.nowiwr01p.domain.create_article.validators.data

interface CreateArticleFieldType

enum class StaticFields: CreateArticleFieldType {
    TOP_IMAGE_FIELD,
    TITLE_FIELD,
    DESCRIPTION_FIELD
}

enum class DynamicFields: CreateArticleFieldType {
    TEXT_FIELD,
    SUBTITLE_FIELD,
    IMAGE_LINK,
    IMAGE_DETAILS,
    ORDERED_LIST_TITLE,
    ORDERED_LIST_STEP
}