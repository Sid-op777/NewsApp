package com.example.newsapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var loadMoreButton: ExtendedFloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //implement other api calls, maybe even have a option to choose where you want your news to be fetched from
        //add a refresh button
        binding =DataBindingUtil.setContentView(this, R.layout.activity_main)

//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
//            insets
//        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.topAppBarLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigation) { v, insets ->
            v.setPadding(0,0,0,0)
            insets
        }

        //handle font


//        recyclerView = binding.newsRecyclerView
        progressBar = binding.progressBar
        loadMoreButton = binding.loadMoreButton

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController


        val repository = NewsRepository(NewsApiClient.apiService)
        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository))[NewsViewModel::class.java]

//        newsAdapter = NewsAdapter(emptyList()) // Initialize with an empty list
//        recyclerView.adapter = newsAdapter
//        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

//        loadMoreButton.setOnClickListener {
//            viewModel.loadMoreNews()
//        }
        //loadMoreButton.visibility = View.GONE

        //handle duplication//done

        val btmNav: BottomNavigationView = binding.bottomNavigation
        btmNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.trending_btn -> {
                    viewModel.clearAllArticles()
                    navController.popBackStack(R.id.trendingFragment,false)
                    navController.navigate(R.id.trendingFragment)
                    loadMoreButton.setOnClickListener {
                        viewModel.loadMoreNews()
                    }
                    true
                }
                R.id.category_btn -> {
                    viewModel.clearAllArticles()
                    navController.popBackStack(R.id.trendingFragment,false)
                    navController.navigate(R.id.categoryFragment)
                    loadMoreButton.setOnClickListener {
                        viewModel.loadMoreCategoryNews()
                    }
                    true
                }
                R.id.search_btn -> {
                    viewModel.clearAllArticles()
                    navController.popBackStack(R.id.trendingFragment,false)
                    navController.navigate(R.id.searchFragment)
                    loadMoreButton.setOnClickListener {
                        viewModel.loadMoreCategoryNews()
                    }
                    true
                }
                else -> false
            }

        }



//        viewModel.newsData.observe(this) { newsResponse ->
//            newsAdapter = NewsAdapter(newsResponse.results)
//            recyclerView.adapter = newsAdapter
//            recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        }

//        viewModel.newsData.observe(this) { newsResponse ->
//            newsAdapter = NewsAdapter(viewModel.allArticles.value?: emptyList())
//            recyclerView.adapter = newsAdapter
//            recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//            // Update existing list instead of creating new adapter
//            //newsAdapter.notifyDataSetChanged()
//        }

        viewModel.hasNextPage.observe(this) { hasNextPage ->
            // Show/hide load more button based on hasNextPage
            loadMoreButton.visibility = if (hasNextPage && !viewModel.isLoading.value!!) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            loadMoreButton.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

//        this.onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
//            override fun handleOnBackPressed(){
//                if(btmNav.selectedItemId!=R.id.trending_btn){
//                    btmNav.selectedItemId = R.id.trending_btn
//                }
//                else{
//                    finish()
//                }
//            }
//        })

        // fab pops only when at the bottom
        //also it keeps going to the top

       //viewModel.fetchNews()

    }
}