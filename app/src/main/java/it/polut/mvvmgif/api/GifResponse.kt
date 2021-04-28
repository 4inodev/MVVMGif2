package it.polut.mvvmgif.api

import com.google.gson.annotations.SerializedName

data class GifResponse(
    @SerializedName("data") val data: List<GifItem>,
    @SerializedName("pagination") val pagination: Pagination
)

data class Pagination(
    @SerializedName("total_count") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int
)