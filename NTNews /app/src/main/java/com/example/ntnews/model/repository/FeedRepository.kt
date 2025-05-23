package com.example.ntnews.model.repository

import com.example.ntnews.data.api.NewsApiService
import com.example.ntnews.model.entities.Article
import com.example.ntnews.model.entities.NewsItem
import com.example.ntnews.utils.ApiResult
import com.example.ntnews.utils.generateNewsItemId
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val apiService: NewsApiService

)
{

    // Fetches top news stories for the specified section
    suspend fun getNews(section: String): ApiResult<List<NewsItem>> {
        return try {
            val response = apiService.getTopStories(section)
            if (response.isSuccessful) {
                val newsList = response.body()?.results?.map { newsItem ->

                    newsItem.copy(id = generateNewsItemId(newsItem.url))
                } ?: emptyList()
                ApiResult.Success(newsList)

            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                ApiResult.Error(Exception("Error fetching top stories: $errorMessage"))

            }
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }



    suspend fun searchArticles(query: String): ApiResult<List<Article>> {

        return try {
            val response = apiService.searchArticles(query = query)
                    if (response.isSuccessful) {
                // Safely unwrap the response body
                val articlesList = response.body()?.response?.docs ?: emptyList()
                                ApiResult.Success(articlesList)
                    } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                ApiResult.Error(Exception("Error searching articles: $errorMessage"))
            }
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}
