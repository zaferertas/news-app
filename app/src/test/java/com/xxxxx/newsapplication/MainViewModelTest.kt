package com.xxxxx.newsapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.xxxxx.newsapplication.data.NewsItem
import com.xxxxx.newsapplication.data.Repository
import com.xxxxx.newsapplication.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val repository = mock(Repository::class.java)
    private val observerBoolean = mock<Observer<Boolean>>()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `repository refresh with success shows error & refreshing list data indicators as false `() {
        runBlocking {

            whenever(repository.refreshNews()).thenReturn(Result.Success("Success"))

            val mainViewModel = MainViewModel(repository)
            mainViewModel.errorRefreshingList.observeForever(observerBoolean)
            mainViewModel.isRefreshing.observeForever(observerBoolean)

            launch(Dispatchers.Main) {
                assertEquals(false, mainViewModel.errorRefreshingList.value)
                assertEquals(false, mainViewModel.isRefreshing.value)
            }

            mainViewModel.isRefreshing.removeObserver(observerBoolean)
            mainViewModel.errorRefreshingList.removeObserver(observerBoolean)
        }
    }

    @Test
    fun `repository refresh with error shows error refreshing list as true`() {
        runBlocking {

            whenever(repository.refreshNews()).thenReturn(Result.Error(IOException("")))
            val mainViewModel = MainViewModel(repository)
            mainViewModel.errorRefreshingList.observeForever(observerBoolean)

            launch(Dispatchers.Main) {
                assertEquals(true, mainViewModel.errorRefreshingList.value)
            }

            mainViewModel.errorRefreshingList.removeObserver(observerBoolean)
        }
    }

    @Test
    fun `news items gets populated`() {
        runBlocking {

            val returnData: LiveData<List<NewsItem>> = MutableLiveData(
                arrayListOf(
                    NewsItem(0, "first", "", "", "", ""),
                    NewsItem(1, "second", "", "", "", "")
                )
            )

            whenever(repository.refreshNews()).thenReturn(Result.Success("Success"))
            whenever(repository.observeNews()).thenReturn(returnData)
            val mainViewModel = MainViewModel(repository)

            launch(Dispatchers.Main) {
                assertEquals(2, mainViewModel.newsList.value?.size)
            }

        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}



