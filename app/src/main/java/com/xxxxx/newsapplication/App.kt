package com.xxxxx.newsapplication

import android.app.Application
import com.xxxxx.newsapplication.di.AppComponent
import com.xxxxx.newsapplication.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}