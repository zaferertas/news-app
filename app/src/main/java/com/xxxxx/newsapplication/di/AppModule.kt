package com.xxxxx.newsapplication.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.xxxxx.newsapplication.MainViewModelFactory
import com.xxxxx.newsapplication.data.Repository
import com.xxxxx.newsapplication.data.local.NewsDao
import com.xxxxx.newsapplication.data.local.NewsDatabase
import com.xxxxx.newsapplication.data.remote.NewsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl("http://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(NewsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): NewsDatabase {
        return Room
            .databaseBuilder(app, NewsDatabase::class.java, "news.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDatabase): NewsDao {
        return db.newsDao()
    }

}

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        repository: Repository
    ): MainViewModelFactory {
        return MainViewModelFactory(
            repository
        )
    }
}