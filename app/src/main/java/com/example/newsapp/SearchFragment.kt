package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.newsapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)

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



        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery = binding.searchEditText.text.toString()
                viewModel.fetchCategoryNews(searchQuery)
                true
            } else {
                false
            }
        }


        return binding.root
    }

}