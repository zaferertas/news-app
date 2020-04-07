package com.xxxxx.newsapplication.data

import androidx.lifecycle.LiveData
import com.xxxxx.newsapplication.BuildConfig
import com.xxxxx.newsapplication.data.local.NewsDao
import com.xxxxx.newsapplication.data.remote.NewsApiService
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApiService: NewsApiService
) {

    private val parentJob = Job()
    private val scope = CoroutineScope(Dispatchers.IO + parentJob)

    suspend fun refreshNews(): Result<Any> {

        val getPropertiesDeferred = newsApiService.getNews("google-news", BuildConfig.API_KEY)
        return try {
            val response = getPropertiesDeferred.await()
            if (response.status == "ok") {
                updateNewsInLocalDB(response.articles)
                Result.Success("Success")
            } else {
                Result.Error(IOException("No Response body"))
            }
        } catch (e: Exception) {
            Result.Error(IOException(e.localizedMessage))
        }
    }

    private fun updateNewsInLocalDB(news: List<NewsItem>) {

        scope.launch {
            newsDao.deleteAllNews()
            news.forEach { newsItem ->
                newsDao.insert(newsItem)
            }
        }
    }

    fun observeNews(): LiveData<List<NewsItem>> {
        return newsDao.observeNews()
    }

    fun onCleared() {
        scope.coroutineContext.cancelChildren()
    }

}