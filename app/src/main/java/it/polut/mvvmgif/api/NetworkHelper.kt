package it.polut.mvvmgif.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var service: ApiService? = null

    fun getService() : ApiService {
        if (service == null) {
            service = retrofit.create(ApiService::class.java)
        }
        return service!!
    }
}