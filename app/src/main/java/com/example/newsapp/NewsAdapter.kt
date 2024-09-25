package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val articles: List<Article>, private val onItemClicked: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headlineTextView: TextView = itemView.findViewById(R.id.headlineTextView)
        val newsImageView: ImageView = itemView.findViewById(R.id.newsImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.headlineTextView.text = article.title
        Glide.with(holder.itemView.context)
            .load(article.image_url)
            .into(holder.newsImageView)
        holder.itemView.setOnClickListener {
            onItemClicked(articles[position])
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}