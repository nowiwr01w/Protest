package com.nowiwr01p.news.ui.article

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.Article.Companion.article
import com.nowiwr01p.core.model.ArticleContentType.*
import com.nowiwr01p.core.model.ContentItem
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
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

    ArticleContent(viewModel.viewState.value, listener)
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
        val contentSorted = article.content.sortedBy { it.order }
        items(contentSorted) { content ->
            NewsItem(content, listener)
        }
        item{ Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
fun NewsItem(content: ContentItem, listener: Listener?) = with(content) {
    when (content.articleType) {
        Title -> TopTitle(value)
        Date -> Date(value.toLong().formatToDateTime())
        Description -> Description(value)
        Image -> TopImage(value)
        Step -> StepItem(value)
        Link -> Link(value, listener)
        OrderedListTitle -> OrderedListTitle(value)
        ImageDescription -> ImageDescription(value)
    }
}

@Composable
fun Toolbar(listener: Listener?) = ToolbarTop(
    backIcon = {
        ToolbarBackButton { listener?.onBackClick() }
    }
)

@Composable
fun TopImage(image: String) = CoilImage(
    imageModel = { image },
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 8.dp, start = 6.dp, end = 6.dp)
        .clip(RoundedCornerShape(16.dp))
)

@Composable
private fun Date(date: String) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
) {
    Text(
        text = date,
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
    Text(
        text = "2752",
        style = MaterialTheme.typography.footnoteRegular,
        color = MaterialTheme.colors.textColorSecondary,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
fun TopTitle(title: String) = Text(
    text = title,
    style = MaterialTheme.typography.title2Bold,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp)
)

@Composable
fun Description(description: String) = Text(
    text = description,
    style = MaterialTheme.typography.body1,
    color = MaterialTheme.colors.textPrimary,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
fun ImageDescription(description: String) = Text(
    text = description,
    style = MaterialTheme.typography.footnoteRegular,
    color = MaterialTheme.colors.textColorSecondary,
    textAlign = TextAlign.Center,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, start = 24.dp, end = 24.dp)
)

@Composable
fun OrderedListTitle(title: String) = Text(
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
private fun Link(text: String, listener: Listener?) = Text(
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

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun Preview() = MeetingsTheme {
    ArticleContent(
        state = State(article = article)
    )
}