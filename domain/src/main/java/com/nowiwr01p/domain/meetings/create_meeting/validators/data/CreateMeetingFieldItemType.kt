package com.nowiwr01p.domain.meetings.create_meeting.validators.data

interface CreateMeetingFieldItemType

enum class CustomTextFieldType: CreateMeetingFieldItemType {
    CITY,
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