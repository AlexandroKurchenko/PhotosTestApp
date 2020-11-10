package com.rollncode.test.repoository

import com.rollncode.test.repoository.db.DataBaseManager
import com.rollncode.test.repoository.db.model.PhotoItem
import com.rollncode.test.repoository.mapper.mapToPhotoItem
import com.rollncode.test.repoository.network.PhotosApi
import com.rollncode.test.repoository.network.model.Photo
import io.reactivex.Observable
import io.reactivex.Single

class PhotosRepositoryImpl(
    private val api: PhotosApi,
    private val dataBaseManager: DataBaseManager
) : PhotosRepository {

    override fun fetchAllPhotos(): Single<MutableList<PhotoItem>> {
        return getApiPhotos().flatMapObservable { photos -> Observable.fromIterable(photos) }
            .take(100)// only for test purpose, because has 5000 items to load
            .map { photo ->
                photo.mapToPhotoItem().also {
                    savePhotoItem(it)
                }
            }
            .toList()
    }

    override fun getAllPhotos(): Single<MutableList<PhotoItem>> {
        return dataBaseManager.getAllPhotos()
            .map { dbPhotos ->
                if (dbPhotos.isEmpty()) {
                    fetchAllPhotos().blockingGet()
                } else {
                    dbPhotos
                }
            }
    }

    private fun savePhotoItem(photoItem: PhotoItem) {
        dataBaseManager.savePhoto(photoItem)
    }

    private fun getApiPhotos(): Single<List<Photo>> = api.fetchAllPhotos()
}