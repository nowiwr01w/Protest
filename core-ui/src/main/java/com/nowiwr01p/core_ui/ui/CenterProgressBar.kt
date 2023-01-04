package com.nowiwr01p.core_ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterScreenProgressBar() = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    CircularProgressIndicator()
}

@Composable
fun CenterScreenProgressBar(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}