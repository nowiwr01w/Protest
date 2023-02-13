package com.nowiwr01p.core_ui.extensions

import androidx.compose.runtime.Composable
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.theme.*

@Composable
fun Category.getColor(index: Int) = when (index) {
    0 -> lightBackgroundBlue to lightGraphicsBlue
    1 -> lightBackgroundGreen to lightGraphicsGreen
    2 -> lightBackgroundRed to lightGraphicsRed
    3 -> lightBackgroundIndigo to lightGraphicsIndigo
    else -> lightBackgroundPurple to lightGraphicsPurple
}