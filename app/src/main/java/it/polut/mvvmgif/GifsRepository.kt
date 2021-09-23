package it.polut.mvvmgif

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import it.polut.mvvmgif.api.GifItem
import it.polut.mvvmgif.api.GifResponse
import it.polut.mvvmgif.api.NetworkHelper
import it.polut.mvvmgif.db.CacheDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GifsRepository {
    private var responseLiveData = MutableLiveData<GifResponse?>()
    private var searchLiveData = MutableLiveData<GifResponse?>()
    private var cacheLiveData = MutableLiveData<List<GifItem>>()

    fun getResponseLiveData(): MutableLiveData<GifResponse?> {
        return responseLiveData
    }

    fun getSearchLiveData(): MutableLiveData<GifResponse?> {
        return searchLiveData
    }
    fun getcacheLiveData(): MutableLiveData<List<GifItem>> {
        return cacheLiveData
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

    fun searchGifs(key: String, limit: Int, q: String) {
        NetworkHelper.getService()
            .searchGif(key, limit, q)
            .enqueue(object : Callback<GifResponse> {
                override fun onFailure(call: Call<GifResponse>, t: Throwable) {
                    responseLiveData.postValue(null)
                }

                override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        searchLiveData.postValue(response.body())
                    }
                }
            })
    }

    fun saveGifsToCache(item: GifResponse){
        insertAsyncTask(cacheDao).execute(item)
    }
    private class insertAsyncTask internal constructor(dao: CacheDao) :
        AsyncTask<GifResponse, Void?, Void?>() {
        private val mAsyncTaskDao: CacheDao
        override fun doInBackground(vararg params: GifResponse): Void? {
            for (item in params[0].data){
                mAsyncTaskDao.addGifToCache(item)
            }
            return null
        }

        init {
            mAsyncTaskDao = dao
        }
    }
}