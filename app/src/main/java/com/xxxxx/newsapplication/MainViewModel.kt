package com.xxxxx.newsapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxxxx.newsapplication.data.NewsItem
import com.xxxxx.newsapplication.data.Repository
import com.xxxxx.newsapplication.data.Result
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var _isRefreshing = MutableLiveData<Boolean>(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private var _errorRefreshingList = MutableLiveData<Boolean>(false)
    val errorRefreshingList: LiveData<Boolean>
        get() = _errorRefreshingList

    var newsList: LiveData<List<NewsItem>>

    private var _selectedNews = MutableLiveData<NewsItem>()
    val selectedNews: LiveData<NewsItem>
        get() = _selectedNews

    init {
        refreshNews()
        newsList = repository.observeNews()
    }

    fun refreshNews() {

        _isRefreshing.value = true

        viewModelScope.launch {
            when (repository.refreshNews()) {
                is Result.Success -> {
                    _isRefreshing.value = false
                }
                is Result.Error -> {
                    _isRefreshing.value = false
                    _errorRefreshingList.value = true
                }
            }
        }
    }

    fun onItemSelected(item: NewsItem) {
        _selectedNews.value = item
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        repository.onCleared()
    }

}