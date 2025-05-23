package com.example.ntnews.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import com.example.ntnews.ui.components.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ntnews.model.entities.Article
import com.example.ntnews.model.entities.NewsItem
import com.example.ntnews.ui.components.CategoryCardComponent
import com.example.ntnews.ui.components.CategoryScrollableRow
import com.example.ntnews.ui.components.SearchCardComponent
import com.example.ntnews.utils.ApiResult
import com.example.ntnews.utils.formatDateTime
import com.example.ntnews.utils.generateNewsItemId
import com.example.ntnews.viewmodel.CategoryViewModel
import com.example.ntnews.viewmodel.FeedViewModel

@Composable
fun CategoryScreen(
    navController: NavController,
    feedViewModel: FeedViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    onCategorySelected: (String) -> Unit = {}
) {
    val context = LocalContext.current

    var query by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val newsItems by feedViewModel.newsItems.collectAsState()
    val searchResults by categoryViewModel.searchResults.collectAsState()

    LaunchedEffect(Unit) {
        feedViewModel.fetchNewsByCategory("World")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Discover",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "News from all around the world",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        SearchBar(
            query = query,
            onQueryChange = {
                query = it
                isSearchActive = query.isNotEmpty()
            },
            onSearch = {
                if (query.isNotEmpty()) {
                    categoryViewModel.fetchArticlesByQuery(query)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = "Search for news..."
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!isSearchActive) {
            CategoryScrollableRow { selectedCategory ->
                feedViewModel.fetchNewsByCategory(selectedCategory)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isSearchActive) {
            when (searchResults) {
                is ApiResult.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ApiResult.Success -> {
                    val results = (searchResults as ApiResult.Success<List<Article>>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(results) { result ->
                            val imageUrl = result.multimedia?.firstOrNull()?.urlArticle?.let {
                                "https://static01.nyt.com/$it"
                            }

                            SearchCardComponent(
                                newsTitle = result.headline.main,
                                newsAuthor = "• " + result.byline,
                                newsDate = result.pubDate.let { formatDateTime(it) } ?: "Unknown Date",
                                newsAbstract = result.abstract ?: "No abstract available",
                                newsSection = result.sectionName,
                                imageUrl = result.multimedia?.firstOrNull()?.urlArticle,
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.webUrl))
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }

                is ApiResult.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        val errorMessage = (searchResults as ApiResult.Error).exception.message
                        Text(text = errorMessage ?: "Error fetching search results", color = Color.Red)
                    }
                }
            }
        } else {
            when (newsItems) {
                is ApiResult.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ApiResult.Success -> {
                    val newsList = (newsItems as ApiResult.Success<List<NewsItem>>).data.sortedByDescending { it.publishedDate }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(newsList) { newsItem ->
                            val newsItemId = generateNewsItemId(newsItem.url)
                            CategoryCardComponent(
                                newsTitle = newsItem.title,
                                newsAuthor = "• " + newsItem.byline,
                                newsDate = newsItem.publishedDate.let { formatDateTime(it) } ?: "Unknown Date", // Null kontrolü
                                newsContent = newsItem.abstract!!,
                                newsSection = newsItem.section,
                                imageUrl = newsItem.multimedia?.firstOrNull()?.url,
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }

                is ApiResult.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        val errorMessage = (newsItems as ApiResult.Error).exception.message
                        Text(text = errorMessage ?: "Error fetching news", color = Color.Red)
                    }
                }
            }
        }
    }
}