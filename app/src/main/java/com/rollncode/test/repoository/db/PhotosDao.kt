package com.rollncode.test.repoository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rollncode.test.repoository.db.model.PhotoItem
import io.reactivex.Single

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoItem(stationList: PhotoItem): Long

    @Query("SELECT * FROM photoItem")
    fun getAllPhotos(): Single<MutableList<PhotoItem>>
}