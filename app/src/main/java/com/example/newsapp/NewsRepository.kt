package com.example.newsapp

import android.util.Log
import com.example.newsapp.NewsApiClient.apiServiceCategory
import com.example.newsapp.NewsApiClient.apiServiceCategoryConti
import com.example.newsapp.NewsApiClient.apiServiceConti
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val apiService: NewsApiService) {

    fun getTopHeadlines(viewModel: NewsViewModel) {
        viewModel.updateLoadingState(true)
        apiService.getHeadlines().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                viewModel.updateLoadingState(false)
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    viewModel.updateCurrentPage(response.body()?.nextPage?: "")
                    if(response.body()?.nextPage ==null){
                        viewModel.updateHasNextPage(false)

                    }
                    else{
                        viewModel.updateHasNextPage(true)
                    }
                    viewModel.updateAllArticles(newsResponse?.results ?: emptyList())
                    Log.d("NewsRepository", "Successfully fetched first page")
                    Log.d("NewsRepository", "Response: ${viewModel.currentPage.value}")
                    viewModel.updateNewsData(newsResponse)
                } else {
                    Log.e("NewsRepository", "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.updateLoadingState(false)
                Log.e("NewsRepository", "Error: ${t.message}")
            }
        })
    }
    //another function to load more
    fun getMoreTopHeadlines(viewModel: NewsViewModel) {
        viewModel.updateLoadingState(true)
        apiServiceConti.getMoreHeadlines(page = viewModel.currentPage.value!!.toLong()).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                viewModel.updateLoadingState(false)
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    viewModel.updateCurrentPage(response.body()?.nextPage?: "")
                    if(response.body()?.nextPage ==null){
                        viewModel.updateHasNextPage(false)
                    }
                    else{
                        viewModel.updateHasNextPage(true)
                    }
                    viewModel.updateAllArticles(newsResponse?.results ?: emptyList())
                    Log.d("NewsRepository", "Response: $newsResponse")
                    viewModel.updateNewsData(newsResponse)
                } else {
                    Log.e("NewsRepository", "ErrorFromServer: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.updateLoadingState(false)
                Log.e("NewsRepository", "Error: ${t.message}")
            }


        })
    }
    //to load first page for category
    fun getTopHeadlinesCategory(viewModel: NewsViewModel) {
        viewModel.updateLoadingState(true)
        apiServiceCategory.getHeadlines(q = viewModel.currentCategory.value?:"").enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                viewModel.updateLoadingState(false)
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    viewModel.updateCurrentPage(response.body()?.nextPage?: "")
                    if(response.body()?.nextPage ==null){
                        viewModel.updateHasNextPage(false)
                    }
                    else{
                        viewModel.updateHasNextPage(true)
                    }
                    viewModel.updateAllArticles(newsResponse?.results ?: emptyList())
                    Log.d("NewsRepository", "Response: $newsResponse")
                    viewModel.updateNewsData(newsResponse)
                } else {
                    Log.e("NewsRepository", "ErrorFromServer: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.updateLoadingState(false)
                Log.e("NewsRepository", "Error: ${t.message}")
            }


        })
    }
    //to load more for category
    fun getMoreTopHeadlinesCategory(viewModel: NewsViewModel){
        apiServiceCategoryConti.getMoreHeadlines(q = viewModel.currentCategory.value?:"", page = viewModel.currentPage.value!!.toLong()).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                viewModel.updateLoadingState(false)
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    viewModel.updateCurrentPage(response.body()?.nextPage?: "")
                    if(response.body()?.nextPage ==null){
                        viewModel.updateHasNextPage(false)
                    }
                    else{
                        viewModel.updateHasNextPage(true)
                    }
                    viewModel.updateAllArticles(newsResponse?.results ?: emptyList())
                    Log.d("NewsRepository", "Response: $newsResponse")
                    viewModel.updateNewsData(newsResponse)
                } else {
                    Log.e("NewsRepository", "ErrorFromServer: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.updateLoadingState(false)
                Log.e("NewsRepository", "Error: ${t.message}")
            }


        })
    }

}