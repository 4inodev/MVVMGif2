package it.polut.mvvmgif.api

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class GifItem(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @ColumnInfo
    @SerializedName("url")
    val url: String,
    @ColumnInfo
    @SerializedName("title")
    val title: String,
    @Embedded
    @SerializedName("images")
    val images: Images
)
@Entity
data class Images(
    @Embedded
    @SerializedName("original")
    val original: ImagesItem,
    @Embedded
    @SerializedName("downsized_medium")
    val downSized: ImagesItem
)
@Entity
data class ImagesItem(
    @ColumnInfo
    @SerializedName("url") val url: String
)