package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String = "Политика",
    val priority: Int = 0,
    val textColor: Long = 0,
    val backgroundColor: Long = 0,
    val isSelected: Boolean = false
) {
    companion object {
        fun getSampleData() = listOf(
            Category(
                name = "Политика",
                priority = 1,
                textColor = 0xFF007AFF,
                backgroundColor = 0xFFE6F2FF
            ),
            Category(
                name = "Права человека",
                priority = 2,
                textColor = 0xFFE34446,
                backgroundColor = 0xFFFCECED
            ),
            Category(
                name = "Преступность",
                priority = 3,
                textColor = 0xFF32B153,
                backgroundColor = 0xFFEBF7EE
            ),
        )
    }
}