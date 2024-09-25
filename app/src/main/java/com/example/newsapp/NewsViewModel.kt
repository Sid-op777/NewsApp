package com.example.newsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _currentCateogry = MutableLiveData<String>()
    val currentCategory: LiveData<String> = _currentCateogry

    private val _allArticles = MutableLiveData<List<Article>>()
    val allArticles: LiveData<List<Article>> = _allArticles

    private val _currentPage = MutableLiveData<String>()
    val currentPage: LiveData<String> = _currentPage

    private val _hasNextPage = MutableLiveData<Boolean>(false)
    val hasNextPage: LiveData<Boolean> = _hasNextPage

    private val _newsData = MutableLiveData<NewsResponse>()
    val newsData: LiveData<NewsResponse> = _newsData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchNews() {
        _isLoading.value = true // Show loading indicator
        viewModelScope.launch {
            try {
                repository.getTopHeadlines(this@NewsViewModel)
            } catch (e: Exception) {
                Log.d("ErrorLoadingFirstPage", e.message.toString())
            }
        }

    }

    fun updateAllArticles(articles: List<Article>) {
        val currentArticles = _allArticles.value ?: emptyList() // Get current articles or empty list
        _allArticles.value = currentArticles + articles
    }

    fun clearAllArticles(){
        _allArticles.value = emptyList()
    }

    fun updateLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    fun updateNewsData(newsResponse: NewsResponse?) {
        _newsData.value = newsResponse ?: NewsResponse("", 0, emptyList(),null)
    }
    fun updateHasNextPage(hasNextPage: Boolean) {
        _hasNextPage.value = hasNextPage
    }
    fun updateCurrentPage(currentPage: String) {
        _currentPage.value = currentPage
    }
    fun updateCurrentCategory(currentCategory: String) {
        _currentCateogry.value = currentCategory
    }
    fun loadMoreNews() {
        if (_hasNextPage.value!! && !(_isLoading.value!!)) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    repository.getMoreTopHeadlines(this@NewsViewModel)
                } catch (e: Exception) {
                    Log.d("ErrorLoadingMore", e.message.toString())
                }
            }
        }
    }

    fun fetchCategoryNews(s: String) {
        Log.d("Category",s)
        _currentCateogry.value = s
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.getTopHeadlinesCategory(this@NewsViewModel)
            } catch (e: Exception) {
                Log.d("ErrorLoadingFirstPage", e.message.toString())
            }
        }
    }

    fun loadMoreCategoryNews() {
        Log.d("Category","${_currentCateogry.value}")
        if (_hasNextPage.value!! && !(_isLoading.value!!)) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    repository.getMoreTopHeadlinesCategory(this@NewsViewModel)
                } catch (e: Exception) {
                    Log.d("ErrorLoadingMore", e.message.toString())
                }
            }
        }
    }


}