package com.rollncode.test.repoository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rollncode.test.repoository.db.model.PhotoItem

@Database(entities = [PhotoItem::class], version = 1)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}