package it.polut.mvvmgif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import it.polut.mvvmgif.databinding.ActivityMainBinding
import it.polut.mvvmgif.posts.CustomRecyclerAdapter
import it.polut.mvvmgif.utils.PagedScrollListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter: CustomRecyclerAdapter? = null
    private var viewModel: MainViewModel? = null

    private var isLoading = false
    private var isLastPage = false
    private var currentOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRecycler()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        isLoading = true
        viewModel?.loadTrending(currentOffset)
    }

    private fun initRecycler() {
        val lm = GridLayoutManager(this, 2)
        binding.gifsRecycler.layoutManager = lm
        adapter = CustomRecyclerAdapter(ArrayList()) {

        }
        binding.gifsRecycler.adapter = adapter
        binding.gifsRecycler.addOnScrollListener(object : PagedScrollListener(lm) {
            override fun canLoad(): Boolean {
                return !isLoading && !isLastPage
            }

            override fun loadMoreItems() {
                isLoading = true
                viewModel?.loadTrending(currentOffset)
            }
        })
    }

    private fun initObservers() {
        viewModel?.responseLiveData?.observe(this, Observer {
            isLoading = false
            if (it != null) {
                adapter?.setData(it.data)
                currentOffset += it.pagination.count
                if (currentOffset >= it.pagination.total) {
                    isLastPage = true
                }
            }
        })
    }
}