package it.polut.mvvmgif.api

import com.google.gson.annotations.SerializedName

data class GifItem(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: Images
)

data class Images(
    @SerializedName("original") val original: ImagesItem,
    @SerializedName("downsized_medium") val downSized: ImagesItem
)

data class ImagesItem(
    @SerializedName("url") val url: String
)