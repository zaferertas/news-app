package com.xxxxx.newsapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxxxx.newsapplication.data.NewsItem

@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}


