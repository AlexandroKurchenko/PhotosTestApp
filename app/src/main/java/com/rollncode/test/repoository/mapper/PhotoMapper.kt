package com.rollncode.test.repoository.mapper

import com.rollncode.test.repoository.db.model.PhotoItem
import com.rollncode.test.repoository.network.model.Photo

private const val PNG_EXT = ".png"
fun Photo.mapToPhotoItem(): PhotoItem {
    return PhotoItem(
        id = this.id,
        title = this.title,
        url = this.url + PNG_EXT,
        thumbnailUrl = this.thumbnailUrl + PNG_EXT
    )
}