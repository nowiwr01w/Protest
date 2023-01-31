package com.nowiwr01p.auth.ui.splash_screen.data

data class ItemData(
    val text: String,
    val value: Float
)

internal fun getAnimatedTextItems() = listOf(
    ItemData(
        text = "На нашем флаге",
        value = 0.2f
    ),
    ItemData(
        text = "Белый снег и синяя река",
        value = 0.4f
    ),
    ItemData(
        text = "И всё.",
        value = 0.6f
    )
)