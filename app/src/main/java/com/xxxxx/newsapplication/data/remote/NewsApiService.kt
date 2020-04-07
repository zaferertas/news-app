package com.xxxxx.newsapplication.data.remote

import com.xxxxx.newsapplication.data.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getNews(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Deferred<NewsResponse>
}