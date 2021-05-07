package it.polut.mvvmgif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import it.polut.mvvmgif.databinding.ActivityMainBinding
import it.polut.mvvmgif.posts.CustomRecyclerAdapter
import it.polut.mvvmgif.utils.PagedScrollListener
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter: CustomRecyclerAdapter? = null
    private var searchAdapter: CustomRecyclerAdapter? = null
    private var viewModel: MainViewModel? = null

    private var timer: Timer? = null

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
        initSearch()
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

        val searchLm = GridLayoutManager(this, 2)
        binding.searchRecycler.layoutManager = searchLm
        searchAdapter = CustomRecyclerAdapter(ArrayList()) {

        }
        binding.searchRecycler.adapter = searchAdapter
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

        viewModel?.searchLiveData?.observe(this, Observer {
            isLoading = false
            if (it != null) {
                searchAdapter?.setData(it.data)
            }
        })
    }

    private fun initSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                if (text.isNotEmpty()) {
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            isLoading = true
                            viewModel?.searchGifs(text)
                            binding.searchRecycler.isVisible = true
                        }
                    }, 600)
                } else {
                    binding.searchRecycler.isVisible = false
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }
}