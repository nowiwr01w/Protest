package com.nowiwr01p.news.ui.news_article

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
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.ArticleContentType.*
import com.nowiwr01p.core.model.ContentItem
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.news.ui.news_article.ArticleContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun ArticleScreen(
    article: Article,
    navigator: Navigator,
    viewModel: ArticleViewModel = getViewModel()
) {
    val state = viewModel.viewState.value

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

    ArticleContent(state, listener)
}

@Composable
fun ArticleContent(
    state: State,
    listener: Listener? = null
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(listener)
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val contentSorted = state.article.content.sortedBy { it.order }
        items(contentSorted) { content ->
            NewsItem(content, listener)
        }
    }
}

@Composable
fun NewsItem(
    content: ContentItem,
    listener: Listener?
) {
    when (content.articleType) {
        Title -> ArticleTitle(content.value)
        Date -> ArticleDate(content.value.toLong().formatToDateTime())
        Description -> ArticleDescription(content.value)
        Image -> ArticleImage(content.value)
        Step -> StepItem(content.value)
        Link -> ArticleLink(content.value, listener)
        OrderedListTitle -> OrderedListTitle(content.value)
    }
}

@Composable
fun Toolbar(listener: Listener?) = ToolbarTop(
    backIcon = {
        ToolbarBackButton {
            listener?.onBackClick()
        }
    }
)

@Composable
fun ArticleTitle(
    title: String,
) = Text(
    text = title,
    style = MaterialTheme.typography.title2Bold,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
fun ArticleDescription(
    description: String
) = Text(
    text = description,
    style = MaterialTheme.typography.body1,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
fun ArticleImage(
    image: String
) = CoilImage(
    imageModel = { image },
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clip(RoundedCornerShape(16.dp))
)

@Composable
fun OrderedListTitle(
    title: String
) = Text(
    text = title,
    style = MaterialTheme.typography.headline,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

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

@Composable
private fun ArticleLink(
    text: String,
    listener: Listener?
) = Text(
    text = text,
    style = MaterialTheme.typography.body1,
    color = MaterialTheme.colors.link,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        .clickable {
            listener?.onLinkClick(text)
        }
)

@Composable
private fun ArticleDate(
    date: String
) = Text(
    text = date,
    style = MaterialTheme.typography.body1,
    color = MaterialTheme.colors.textColorSecondary,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Preview(showBackground = true)
@Composable
fun Preview() = MeetingsTheme {
    ArticleContent(State(article = Article.article))
}