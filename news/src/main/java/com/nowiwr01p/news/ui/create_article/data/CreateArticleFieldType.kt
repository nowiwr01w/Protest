package com.nowiwr01p.news.ui.create_article.data

interface CreateArticleFieldType

enum class StaticFields: CreateArticleFieldType {
    TOP_IMAGE_FIELD,
    TITLE_FIELD,
    DESCRIPTION_FIELD
}

enum class DynamicFields: CreateArticleFieldType {
    SUBTITLE_FIELD,
    IMAGE_LINK,
    IMAGE_DETAILS,
    ORDERED_LIST_TITLE,
    ORDERED_LIST_STEP
}

enum class DynamicFieldItem: CreateArticleFieldType {
    DYNAMIC
}