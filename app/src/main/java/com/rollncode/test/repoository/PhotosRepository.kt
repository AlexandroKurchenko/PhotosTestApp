package com.rollncode.test.repoository

import com.rollncode.test.repoository.db.model.PhotoItem
import io.reactivex.Single

interface PhotosRepository {
    fun fetchAllPhotos(): Single<MutableList<PhotoItem>>
    fun getAllPhotos(): Single<MutableList<PhotoItem>>
}