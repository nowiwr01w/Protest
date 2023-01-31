package com.nowiwr01p.core_ui.ui.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.mainBackgroundColor

@Composable
fun CenterScreenProgressBar() = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = MaterialTheme.colors.mainBackgroundColor,
        strokeWidth = 2.dp
    )
}

@Composable
fun StubProgressBar(modifier: Modifier = Modifier) = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
        .fillMaxSize()
        .background(Color.White)
) {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = MaterialTheme.colors.mainBackgroundColor,
        strokeWidth = 2.dp
    )
}

@Composable
fun CenterScreenProgressBar(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}