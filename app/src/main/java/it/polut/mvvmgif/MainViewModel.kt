package it.polut.mvvmgif

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import it.polut.mvvmgif.api.GifResponse

class MainViewModel : ViewModel() {
    var responseLiveData = MutableLiveData<GifResponse?>()
    var searchLiveData = MutableLiveData<GifResponse?>()
    private var repo: GifsRepository? = null

    init {
        repo = GifsRepository()
        responseLiveData = repo!!.getResponseLiveData()
        searchLiveData = repo!!.getSearchLiveData()
    }

    fun loadTrending(offset: Int) {
        repo?.loadGifs("CtRYArKhb4YGx41Z7h0wR3h0SAyVRxWr", 30, offset)
    }

    fun searchGifs(q: String) {
        repo?.searchGifs("CtRYArKhb4YGx41Z7h0wR3h0SAyVRxWr", 30, q)
    }

}