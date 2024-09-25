package com.example.newsapp

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>,
    val nextPage: String?
)
