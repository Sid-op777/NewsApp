package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiCategoryServiceConti {
    @GET("latest")
    fun getMoreHeadlines(
        @Query("apiKey") apiKey: String = "pub_54168f20c56d764fbf84c8654293f4b287773",
        @Query("country") country: String = "in",
        @Query("language") language: String = "en",
        @Query("page") page: Long,
        @Query("q") q: String
    ): Call<NewsResponse>
}