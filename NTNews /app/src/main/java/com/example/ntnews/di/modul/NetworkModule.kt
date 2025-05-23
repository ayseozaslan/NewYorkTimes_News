package com.example.ntnews.di.modul

import android.util.Log
import com.example.ntnews.data.api.NewsApiService
import com.example.ntnews.model.entities.Article
import com.example.ntnews.model.entities.ArticleDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.nytimes.com/"
    // Base URL for the API

    @Provides
    @Singleton
    fun provideGson(): Gson {
        Log.d("GSON_CHECK", "Custom Gson instance is being created")
        return GsonBuilder()
            .registerTypeAdapter(Article::class.java, ArticleDeserializer())
            .create()
    }

    // Provides a singleton instance of Retrofit for making network requests
    @Provides
    @Singleton
    fun provideRetrofit(gson:Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))// <-- inject edilen gson kullanılıyor
            .build()
    }

    // Provides a singleton instance of NewsApiService using Retrofit

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)

    }
}