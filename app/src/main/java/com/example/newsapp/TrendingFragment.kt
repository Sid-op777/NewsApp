package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.newsapp.databinding.FragmentTrendingBinding


class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_trending,container,false)

        recyclerView = binding.newsRecyclerView

//        repository = NewsRepository(NewsApiClient.apiService)
//        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository))[NewsViewModel::class.java]

        val viewModel: NewsViewModel by activityViewModels()

        viewModel.newsData.observe(viewLifecycleOwner) { newsResponse ->
            //newsAdapter = NewsAdapter(viewModel.allArticles.value?: emptyList())
            newsAdapter = NewsAdapter(viewModel.allArticles.value ?: emptyList()) { article ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.link))
                startActivity(intent)
            }
            recyclerView.adapter = newsAdapter
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            // Update existing list instead of creating new adapter
            //newsAdapter.notifyDataSetChanged()
        }

        viewModel.fetchNews()

        return binding.root
    }
}