package com.nowiwr01p.domain.cteate_meeting.validators.data

interface CreateMeetingFieldItemType

enum class CustomTextFieldType: CreateMeetingFieldItemType {
    TOP_IMAGE,
    CATEGORIES,
    TITLE,
    DESCRIPTION,
    DATE,
    OPEN_DATE,
    LOCATION_COORDINATES,
    LOCATION_TITLE,
    LOCATION_DETAILS,
    PATH,
    TELEGRAM,
    POSTER_MOTIVATION
}

enum class DetailsFieldType: CreateMeetingFieldItemType {
    POSTER_LINKS,
    GOALS,
    SLOGANS,
    STRATEGY
}

enum class DynamicDetailsItem: CreateMeetingFieldItemType {
    DYNAMIC
}