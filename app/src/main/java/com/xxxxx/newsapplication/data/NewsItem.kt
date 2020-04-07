package com.xxxxx.newsapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
class NewsItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,

    @SerializedName("urlToImage")
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String,

    @SerializedName("publishedAt")
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @SerializedName("content")
    @ColumnInfo(name = "content")
    val content: String
)
