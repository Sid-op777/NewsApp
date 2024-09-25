package com.example.newsapp

//data class Article(
//    val source: Source,
//    val author: String?,
//    val title: String,
//    val description: String?,
//    val url: String,
//    val urlToImage: String?,
//    val publishedAt: String,
//    val content: String?
//)

data class Article(
    val article_id: String,
    val title: String,
    val link: String,
    val keywords: List<String>?,
    val creator: List<String>,
    val video_url: String?,
    val description: String?,
    val content: String,
    val pubDate: String,
    val pubDateTZ: String,
    val image_url: String?,
    val source_id: String,
    val source_priority: Int,
    val source_name: String,
    val source_url: String,
    val source_icon: String?,
    val language: String,
    val country: List<String>,
    val category: List<String>,
    val ai_tag:String,
    val sentiment: String,
    val sentiment_stats:String,
    val ai_region:String,
    val ai_org:String,
    val duplicate:Boolean
)


