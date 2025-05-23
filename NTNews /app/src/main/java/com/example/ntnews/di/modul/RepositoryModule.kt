package com.example.ntnews.di.modul

import com.example.ntnews.data.api.NewsApiService
import com.example.ntnews.model.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Provides a singleton instance of FeedRepository
    @Provides
    @Singleton
    fun provideFeedRepository(
        apiService: NewsApiService
    ): FeedRepository {
        return FeedRepository(apiService)
    }
}