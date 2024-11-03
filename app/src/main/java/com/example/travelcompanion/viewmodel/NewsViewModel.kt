package com.example.travelcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelcompanion.model.NewsResponse
import com.example.travelcompanion.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository()
    private val _news = MutableStateFlow<NewsResponse?>(null)
    val news: StateFlow<NewsResponse?> = _news
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchNews(query: String) {
        viewModelScope.launch {
            newsRepository.getNews(query, "ea0d695e405648f9a27be762f45da118").collect { result ->
                result.fold(
                    onSuccess = { newsResponse ->
                        _news.value = newsResponse
                        _error.value = null
                    },
                    onFailure = { exception ->
                        _news.value = null
                        _error.value = exception.message
                    }
                )
            }
        }
    }

    fun fetchTopHeadlines() {
        viewModelScope.launch {
            newsRepository.getTopHeadlines("ea0d695e405648f9a27be762f45da118", "fr").collect { result ->
                result.fold(
                    onSuccess = { newsResponse ->
                        _news.value = newsResponse
                        _error.value = null
                    },
                    onFailure = { exception ->
                        _news.value = null
                        _error.value = exception.message
                    }
                )
            }
        }
    }
}
