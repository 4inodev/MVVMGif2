package it.polut.mvvmgif.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
// some changes
interface ApiService {
    @GET("/v1/gifs/trending")
    fun getTrendingGifs(
        @Query("api_key") key: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<GifResponse>

    @GET("/v1/gifs/search")
    fun searchGif(
        @Query("api_key") key: String,
        @Query("limit") limit: Int,
        @Query("q") query: String
    ): Call<GifResponse>
}