package com.xxxxx.newsapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xxxxx.newsapplication.data.NewsItem

@Dao
interface NewsDao {

    @Insert
    fun insert(newsItem: NewsItem)

    @Query("SELECT * FROM news")
    fun observeNews(): LiveData<List<NewsItem>>

    @Query("DELETE FROM news")
    fun deleteAllNews()
}
