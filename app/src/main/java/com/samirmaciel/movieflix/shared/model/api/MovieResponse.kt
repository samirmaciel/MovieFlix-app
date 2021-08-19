package com.samirmaciel.movieflix.shared.model.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieResponse (

    @SerializedName("results")
    val movieList : MutableList<MovieEntityApi>


) : Parcelable{
    constructor() : this(mutableListOf())
}