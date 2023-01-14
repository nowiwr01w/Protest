package com.nowiwr01p.core.model

enum class CreateMeetingMapType(val type: String) {
    DRAW_PATH("draw_path"),
    SELECT_START_LOCATION("select_start_location");

    companion object {
        fun findByType(type: String) = values().find {
            it.type.equals(type, ignoreCase = true)
        } ?: DRAW_PATH
    }
}