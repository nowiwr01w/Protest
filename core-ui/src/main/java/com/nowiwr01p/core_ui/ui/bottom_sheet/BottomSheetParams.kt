package com.nowiwr01p.core_ui.ui.bottom_sheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BottomSheetParams(
    val topPadding: Dp = 0.dp,
    val closeAfterClick: Boolean = false,
    val content: @Composable () -> Unit
)