package com.jobayr.bcm.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    var name: String? = null,
    var edition: String? = null,
    var author: String? = null,
    var coverUrl: String? = null,
    var downloadUrl: String? = null,
    var uploaderName: String? = null,
    var uploaderID: String? = null,
    var departmentID: Int? = null,
    var uploadDate: Long? = null
) : Parcelable