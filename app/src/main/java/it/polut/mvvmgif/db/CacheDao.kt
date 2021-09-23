package it.polut.mvvmgif.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import it.polut.mvvmgif.api.GifItem

@Dao
interface CacheDao {
    @Query("SELECT * FROM GifItem")
    public fun getAllGifs():LiveData<List<GifItem>>

    @Insert
    public fun addGifToCache(item: GifItem)

    @Query("DELETE FROM GifItem")
    public fun clearCache()

}
