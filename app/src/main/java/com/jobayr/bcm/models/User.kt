package com.jobayr.bcm.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var dept: Int = -1
) : Parcelable {
}