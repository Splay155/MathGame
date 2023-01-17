package com.example.uts_mpr

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class highscore(
    var name: String,
    var description: String,
    var photo: Int,
    val score: String ,
    ) : Parcelable