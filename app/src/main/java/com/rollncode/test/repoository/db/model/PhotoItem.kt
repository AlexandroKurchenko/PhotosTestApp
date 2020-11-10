package com.rollncode.test.repoository.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PhotoItem(
    @PrimaryKey val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable