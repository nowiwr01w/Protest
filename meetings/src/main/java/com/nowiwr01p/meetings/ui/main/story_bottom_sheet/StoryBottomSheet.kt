package com.nowiwr01p.meetings.ui.main.story_bottom_sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.domain.meetings.data.*
import com.skydoves.landscapist.coil.CoilImage

/**
 * STORY BOTTOM SHEET
 */
internal fun StoryBottomSheet(story: Story): @Composable () -> Unit = {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(story.getContent()) { item ->
            when (item) {
                is Image -> Image(item)
                is Title -> Title(item.text)
                is Description -> Description(item.text)
                is OrderedList -> OrderedListItem(item)
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun Title(text: String) = Text(
    text = text,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp)
)

@Composable
private fun Description(text: String) = Text(
    text = text,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun Image(image: Image) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, bottom = 4.dp),
) {
    CoilImage(
        imageModel = { image.link },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
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

@Composable
private fun OrderedListItem(orderedList: OrderedList) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
) {
    Text(
        text = orderedList.title,
        style = MaterialTheme.typography.title3Bold,
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
