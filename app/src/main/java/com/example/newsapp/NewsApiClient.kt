package com.example.newsapp


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiClient {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    val apiServiceConti: NewsApiServiceConti by lazy {
        retrofit.create(NewsApiServiceConti::class.java)
    }

    val apiServiceCategory: NewsApiCategoryService by lazy {
        retrofit.create(NewsApiCategoryService::class.java)
    }

    val apiServiceCategoryConti: NewsApiCategoryServiceConti by lazy {
        retrofit.create(NewsApiCategoryServiceConti::class.java)
    }




}