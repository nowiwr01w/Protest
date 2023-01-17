package com.nowiwr01p.news.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.ArticleData
import com.nowiwr01p.core.model.ArticleData.*
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.ClickableIcon
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.news.R
import com.nowiwr01p.news.ui.news.NewsContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun NewsMainScreen(
    navigator: Navigator,
    viewModel: NewsViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onArticleClick(article: Article) {
            viewModel.setEvent(Event.OnArticleClick(article))
        }
        override fun toCreateArticle() {
            viewModel.setEvent(Event.NavigateToCreateArticle)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.ShowArticle -> {
                navigator.newsNavigator.navigateToArticle(it.article)
            }
            is Effect.NavigateToCreateArticle -> {
                navigator.newsNavigator.navigateToCreateArticle()
            }
        }
    }

    NewsContent(viewModel.viewState.value, listener)
}

@Composable
fun NewsContent(state: State, listener: Listener?) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(state, listener)
    if (state.isLoading) {
        CenterScreenProgressBar()
    } else {
        NewsList(state, listener)
    }
}

@Composable
fun Toolbar(state: State, listener: Listener?) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(top = 20.dp),
) {
    Text(
        text = "Новости",
        style = MaterialTheme.typography.largeTitle,
        color = MaterialTheme.colors.textPrimary,
        modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(
        modifier = Modifier.weight(1f)
    )
    ClickableIcon(
        icon = R.drawable.ic_add,
        modifier = Modifier.padding(end = 16.dp),
        onClick = { listener?.toCreateArticle() }
    )
}

@Composable
fun NewsList(state: State, listener: Listener?) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(top = 12.dp)
) {
    items(state.newsList) { article ->
        ArticleView(article, listener)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ArticleView(article: Article, listener: Listener?) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { listener?.onArticleClick(article) }
        .padding(vertical = 4.dp)
        .background(MaterialTheme.colors.background)
) {
    val (image, title, date, viewsCount) = createRefs()

    val imageModifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(horizontal = 6.dp)
        .clip(RoundedCornerShape(16.dp))
        .constrainAs(image) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    CoilImage(
        modifier = imageModifier,
        imageModel = { article.topImage.link }
    )

    val titleModifier = Modifier
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        .constrainAs(title) {
            width = Dimension.fillToConstraints
            start.linkTo(image.start)
            end.linkTo(image.end)
            top.linkTo(image.bottom)
        }

    Text(
        text = article.title.text,
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.title3Bold,
        modifier = titleModifier
    )

    val dateModifier = Modifier
        .padding(top = 4.dp, start = 16.dp)
        .constrainAs(date) {
            start.linkTo(title.start)
            top.linkTo(title.bottom)
        }
    Text(
        text = article.dateViewers.date.formatToDateTime(),
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.footnoteRegular,
        modifier = dateModifier
    )

    val viewsCountModifier = Modifier
        .padding(end = 16.dp)
        .constrainAs(viewsCount) {
            end.linkTo(title.end)
            top.linkTo(date.top)
            bottom.linkTo(date.bottom)
        }
    Row(
        modifier = viewsCountModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_eye),
            contentDescription = "Views count icon",
            tint = MaterialTheme.colors.textColorSecondary,
            modifier = Modifier.size(17.dp)
        )
        Text(
            text = article.dateViewers.viewers.size.toString(),
            style = MaterialTheme.typography.footnoteRegular,
            color = MaterialTheme.colors.textColorSecondary,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
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