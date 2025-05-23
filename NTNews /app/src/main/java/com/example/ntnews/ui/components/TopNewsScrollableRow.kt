package com.example.ntnews.ui.components

import android.text.format.DateUtils.formatDateTime
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ntnews.model.entities.NewsItem
import com.example.ntnews.ui.theme.Redwood
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import coil.compose.rememberImagePainter
import androidx.compose.foundation.lazy.items
import com.example.ntnews.utils.formatDateTime
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun TopNewsScrollableRow(
    topNewsItems: List<NewsItem>,
    onNewsClick: (NewsItem) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableStateOf(0) }

    val density = LocalDensity.current

    val itemWidthPx = with(density) { 300.dp.toPx() }
    val itemSpacingPx = with(density) { 6.dp.toPx() }


    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(topNewsItems) { newsItem ->
                TopNewsCard(
                    newsItem = newsItem,
                    onClick = { onNewsClick(newsItem) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(topNewsItems.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (index == currentIndex) Color.Black else Color.LightGray)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        }
                )
            }
        }


        LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
            val visibleItemIndex = listState.firstVisibleItemIndex
            val visibleItemOffset = listState.firstVisibleItemScrollOffset.toFloat()
            val totalItemWidth = itemWidthPx + itemSpacingPx
            val offsetPercentage = visibleItemOffset / totalItemWidth

            currentIndex = (visibleItemIndex + offsetPercentage.roundToInt()).coerceIn(0, topNewsItems.size - 1)

        }
    }

}

@Composable
fun TopNewsCard(newsItem: NewsItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(300.dp)
            .width(400.dp)
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (newsItem.multimedia?.firstOrNull()?.url != null) {

                Image(
                    painter = rememberImagePainter(newsItem.multimedia.firstOrNull()?.url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = newsItem.section.uppercase(),
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Redwood, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatDateTime(newsItem.publishedDate),
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(Redwood.copy(0.9f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = newsItem.title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

