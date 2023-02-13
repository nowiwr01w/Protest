package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String = "",
    val priority: Int = 0,
    val isSelected: Boolean = false
) {
    companion object {
        fun getSampleData() = listOf(
            Category(
                name = "Политика",
                priority = 1
            ),
            Category(
                name = "Права человека",
                priority = 2
            ),
            Category(
                name = "Преступность",
                priority = 3
            )
        )
    }
}