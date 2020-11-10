package com.rollncode.test.repoository.db

import com.rollncode.test.repoository.db.model.PhotoItem
import io.reactivex.Single

class DataBaseManager(private val db: PhotosDatabase) {

    fun savePhoto(photo: PhotoItem) {
        db.photosDao().insertPhotoItem(photo)
    }

    fun getAllPhotos(): Single<MutableList<PhotoItem>> {
        return db.photosDao().getAllPhotos()
    }
}