package com.samirmaciel.movieflix.shared.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    @SerializedName("id")
    val id : String?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("poster_path")
    val poster : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("release_date")
    val realese : String?


) : Parcelable{
    constructor() : this("", "", "", "", "")
}
