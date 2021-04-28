package it.polut.mvvmgif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import it.polut.mvvmgif.api.GifResponse
import it.polut.mvvmgif.api.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GifsRepository {
    private var responseLiveData = MutableLiveData<GifResponse?>()

    fun getResponseLiveData(): MutableLiveData<GifResponse?> {
        return responseLiveData
    }

    fun loadGifs(key: String, limit: Int, offset: Int) {
        NetworkHelper.getService()
            .getTrendingGifs(key, limit, offset)
            .enqueue(object : Callback<GifResponse> {
                override fun onFailure(call: Call<GifResponse>, t: Throwable) {
                    responseLiveData.postValue(null)
                }

                override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        responseLiveData.postValue(response.body())
                    }
                }
            })
    }
}