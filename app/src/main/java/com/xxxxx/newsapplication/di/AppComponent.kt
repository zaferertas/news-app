package com.xxxxx.newsapplication.di

import android.app.Application
import com.xxxxx.newsapplication.DetailsFragment
import com.xxxxx.newsapplication.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailsFragment)
}
