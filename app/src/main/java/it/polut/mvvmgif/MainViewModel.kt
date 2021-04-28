package it.polut.mvvmgif

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import it.polut.mvvmgif.api.GifResponse

class MainViewModel : ViewModel() {
    var responseLiveData = MutableLiveData<GifResponse?>()
    private var repo: GifsRepository? = null

    init {
        repo = GifsRepository()
        responseLiveData = repo!!.getResponseLiveData()
    }

    fun loadTrending(offset: Int) {
        repo?.loadGifs("CtRYArKhb4YGx41Z7h0wR3h0SAyVRxWr", 30, offset)
    }

}