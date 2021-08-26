package com.samirmaciel.movieflix.shared.model.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ActorModel(

    @SerializedName("id")
    val id : String?,

    @SerializedName("name")
    val name : String?,

    @SerializedName("known_for_department")
    val known_for_department : String?,

    @SerializedName("profile_path")
    val profile_path : String?,

    @SerializedName("character")
    val character : String?
): Parcelable {
    constructor() : this("", "", "", "","")
}
