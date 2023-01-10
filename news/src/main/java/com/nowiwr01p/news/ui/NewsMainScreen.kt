package com.nowiwr01p.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nowiwr01p.core.extenstion.formatToDate
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.ArticleContentType.Image
import com.nowiwr01p.core.model.ArticleContentType.Title
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.news.ui.NewsContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun NewsMainScreen(
    navigator: Navigator,
    viewModel: NewsViewModel = getViewModel()
) {
    val state = viewModel.viewState.value
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    NewsContent(state, listener)
}

@Composable
fun NewsContent(
    state: State,
    listener: Listener?
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar()
    Spacer(modifier = Modifier.height(16.dp))
    NewsList(state, listener)
}

@Composable
fun Toolbar() =
    Text(
        text = "Новости",
        style = MaterialTheme.typography.largeTitle,
        color = MaterialTheme.colors.textPrimary,
        modifier = Modifier
            .statusBarsPadding()
            .padding(start = 20.dp, top = 20.dp)
    )

@Composable
fun NewsList(
    state: State,
    listener: Listener?
) = LazyColumn(
    modifier = Modifier.fillMaxSize()
) {
    items(state.newsList) { article ->
        ArticleView(article, listener)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ArticleView(
    article: Article,
    listener: Listener?
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { }
        .padding(vertical = 12.dp, horizontal = 16.dp)
        .background(MaterialTheme.colors.background)
) {
    val (image, title, date) = createRefs()

    val imageModifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .constrainAs(image) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    CoilImage(
        modifier = imageModifier,
        imageModel = { article.getField(Image) }
    )

    val titleModifier = Modifier
        .padding(top = 8.dp)
        .constrainAs(title) {
            width = Dimension.fillToConstraints
            start.linkTo(image.start)
            end.linkTo(image.end)
            top.linkTo(image.bottom)
        }

    Text(
        text = article.getField(Title),
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.title2Bold,
        modifier = titleModifier
    )

    val dateModifier = Modifier
        .padding(top = 4.dp)
        .constrainAs(date) {
            start.linkTo(title.start)
            top.linkTo(title.bottom)
        }
    Text(
        text = article.date.formatToDate(),
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = dateModifier
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() = MeetingsTheme {
    NewsContent(
        state = State(
            isLoading = false,
            newsList = listOf(Article.article)
        ),
        listener = null
    )
}