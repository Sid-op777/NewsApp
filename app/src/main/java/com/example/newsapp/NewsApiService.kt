package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//use this for the trending page
//it returns no images also it directly links to the url
//https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=API_KEY

//temp change
//https://newsapi.org/v2/top-headlines?country=us&apiKey=1751159bae674edcbcd20c3fac9f8736
//interface NewsApiService {
//    @GET("top-headlines")
//    fun getHeadlines(
//        @Query("apiKey") apiKey: String,
//        @Query("country") country: String = "us"
//        //@Query("pageSize") pageSize: Int = 50
//    ): Call<NewsResponse>
//}

interface NewsApiService {
    @GET("latest")
    fun getHeadlines(
        @Query("apiKey") apiKey: String = "pub_54168f20c56d764fbf84c8654293f4b287773",
        @Query("country") country: String = "in",
        @Query("language") language: String = "en"
    ): Call<NewsResponse>
}

