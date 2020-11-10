package com.rollncode.test.repoository.network

import com.rollncode.test.repoository.network.model.Photo
import io.reactivex.Single
import retrofit2.http.GET

interface PhotosApi {
    @GET("photos")
    fun fetchAllPhotos(): Single<List<Photo>>
}