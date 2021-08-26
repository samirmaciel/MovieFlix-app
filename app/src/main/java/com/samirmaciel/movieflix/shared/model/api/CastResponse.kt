package com.samirmaciel.movieflix.shared.model.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CastResponse(

    @SerializedName("cast")
    val castList : MutableList<ActorModel>


) : Parcelable {
    constructor() : this(mutableListOf())
}
