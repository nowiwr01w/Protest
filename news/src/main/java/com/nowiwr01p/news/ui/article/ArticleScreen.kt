@file:OptIn(ExperimentalPagerApi::class)

package com.nowiwr01p.news.ui.article

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.model.*
import com.nowiwr01p.core.model.Article.Companion.article
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.horizontal_pager.PagerIndicator
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.news.R
import com.nowiwr01p.news.ui.article.ArticleContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun ArticleScreen(
    article: Article,
    navigator: Navigator,
    viewModel: ArticleViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onLinkClick(link: String) {
            viewModel.setEvent(Event.OpenLink(link))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(article))
    }

    ArticleContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
fun ArticleContent(state: State, listener: Listener?) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(listener)
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val sortedContent = state.article.buildContent()
        items(sortedContent) { content ->
            when (content) {
                is TopImage -> TopImage(content)
                is DateViewers -> Date(state, content)
                is Title -> TopTitle(content)
                is Description -> Description(content)
                is ImageList -> ImageListItem(content)
                is OrderedList -> OrderedListItem(content)
                else -> {}
            }
        }
        item{ Spacer(modifier = Modifier.height(32.dp)) }
    }
}

/**
 * TOOLBAR
 */
@Composable
fun Toolbar(listener: Listener?) = ToolbarTop(
    backIcon = {
        ToolbarBackButton { listener?.onBackClick() }
    }
)

/**
 * TOP IMAGE
 */
@Composable
fun TopImage(image: TopImage) = CoilImage(
    imageModel = { image.link },
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 8.dp, start = 6.dp, end = 6.dp)
        .clip(RoundedCornerShape(16.dp))
)

/**
 * DATE
 */
@Composable
private fun Date(state: State, date: DateViewers) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
) {
    Text(
        text = date.date.formatToDateTime(),
        style = MaterialTheme.typography.footnoteRegular,
        color = MaterialTheme.colors.textColorSecondary
    )
    Spacer(
        modifier = Modifier.weight(1f)
    )
    Icon(
        painter = painterResource(R.drawable.ic_eye),
        contentDescription = "Views count icon",
        tint = MaterialTheme.colors.textColorSecondary,
        modifier = Modifier.size(17.dp)
    )
    val viewsCount = if (state.viewsCount == 0) date.viewers.size else state.viewsCount
    Text(
        text = viewsCount.toString(),
        style = MaterialTheme.typography.footnoteRegular,
        color = MaterialTheme.colors.textColorSecondary,
        modifier = Modifier.padding(start = 8.dp)
    )
}

/**
 * TOP TITLE
 */
@Composable
private fun TopTitle(title: Title) = Text(
    text = title.text,
    style = MaterialTheme.typography.title2Bold,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp)
)

/**
 * DESCRIPTION
 */
@Composable
private fun Description(description: Description) = Text(
    text = description.text,
    style = MaterialTheme.typography.body1,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

/**
 * IMAGES
 */
@Composable
private fun ImageListItem(imageList: ImageList) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp)
) {
    val (pager, dots) = createRefs()
    val state = rememberPagerState()

    val pagerModifier = Modifier
        .fillMaxWidth()
        .constrainAs(pager) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    HorizontalPager(
        count = imageList.images.size,
        state = state,
        modifier = pagerModifier
    ) { page ->
        val image = imageList.images[page]
        ImageItem(image)
    }

    if (imageList.images.size > 1) {
        val dotsModifier = Modifier
            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
            .constrainAs(dots) {
                start.linkTo(pager.start)
                end.linkTo(pager.end)
                top.linkTo(pager.bottom)
            }
        PagerIndicator(
            pagerState = state,
            space = 4.dp,
            indicatorSize = 12.dp,
            indicatorCount = imageList.images.size,
            modifier = dotsModifier
        )
    }
}

@Composable
private fun ImageItem(image: Image) = Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    CoilImage(
        imageModel = { image.link },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(start = 6.dp, end = 6.dp)
            .clip(RoundedCornerShape(16.dp))
    )
    Text(
        text = image.description,
        style = MaterialTheme.typography.footnoteRegular,
        color = MaterialTheme.colors.textColorSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 24.dp, end = 24.dp)
    )
}

/**
 * ORDERED LIST
 */
@Composable
private fun OrderedListItem(orderedList: OrderedList) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
) {
    Text(
        text = orderedList.title,
        style = MaterialTheme.typography.headline,
        color = MaterialTheme.colors.textPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
    orderedList.steps.forEach { item ->
        StepItem(item)
    }
}

@Composable
private fun StepItem(text: String) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 20.dp, end = 20.dp)
) {
    Text(
        text = "–",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.textPrimary
    )
    Spacer(modifier = Modifier.width(12.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.textPrimary
    )
}

/**
 * PREVIEW
 */
@Preview(showBackground = true, heightDp = 1600)
@Composable
fun Preview() = MeetingsTheme {
    ArticleContent(
        state = State(article = article),
        listener = null
    )
}