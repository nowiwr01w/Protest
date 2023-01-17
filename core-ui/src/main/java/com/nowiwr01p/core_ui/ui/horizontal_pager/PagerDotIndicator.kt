package com.nowiwr01p.core_ui.ui.horizontal_pager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.nowiwr01p.core_ui.theme.mainBackgroundColor

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorCount: Int = 5,
    indicatorSize: Dp = 6.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = 3.dp,
    activeColor: Color = Color(0xFFFC4C4C),
    inActiveColor: Color = MaterialTheme.colors.mainBackgroundColor,
    onClick: ((Int) -> Unit)? = null
) {
    val listState = rememberLazyListState()

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    val itemCount = pagerState.pageCount
    val currentItem = pagerState.currentPage

    LaunchedEffect(currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            currentItem,
            (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }


    LazyRow(
        modifier = modifier.width(totalWidth),
        state = listState,
        contentPadding = PaddingValues(vertical = space),
        horizontalArrangement = Arrangement.spacedBy(space),
        userScrollEnabled = false
    ) {

        items(itemCount) { index ->
            val isSelected = index == currentItem
            val scale = if (isSelected) 1f else 0.5f
            Box(
                modifier = Modifier
                    .scale(scale)
                    .clip(indicatorShape)
                    .size(indicatorSize)
                    .background(
                        color = if (isSelected) activeColor else inActiveColor,
                        shape = indicatorShape
                    )
                    .then(
                        if (onClick != null) {
                            Modifier.clickable { onClick.invoke(index) }
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}