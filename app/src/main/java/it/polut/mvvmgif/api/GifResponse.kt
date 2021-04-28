package it.polut.mvvmgif.api

import com.google.gson.annotations.SerializedName

data class GifResponse(
    @SerializedName("data") var data: List<GifItem>,
    @SerializedName("pagination") var pagination: Pagination
)

data class Pagination(
    @SerializedName("total_count") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int
)