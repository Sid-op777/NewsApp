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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.newsapp.databinding.FragmentCategoryBinding
import com.google.android.material.tabs.TabLayout

class CategoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category,container,false)

        recyclerView = binding.categoryRecyclerView

        val viewModel: NewsViewModel by activityViewModels()

        viewModel.newsData.observe(viewLifecycleOwner) { newsResponse ->
            //newsAdapter = NewsAdapter(viewModel.allArticles.value?: emptyList())
            newsAdapter = NewsAdapter(viewModel.allArticles.value ?: emptyList()) { article ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.source_url))
                startActivity(intent)
            }
            recyclerView.adapter = newsAdapter
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            // Update existing list instead of creating new adapter
            //newsAdapter.notifyDataSetChanged()
        }

        binding.categoryTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.clearAllArticles()
                val selectedTabName = tab?.text.toString()
                viewModel.fetchCategoryNews(selectedTabName)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not needed for this case
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not needed for this case
            }
        })


        viewModel.fetchCategoryNews("Technology")

        return binding.root
    }

}