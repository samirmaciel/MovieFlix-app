package com.samirmaciel.movieflix.shared.model.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.samirmaciel.movieflix.shared.model.local.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    @SerializedName("id")
    val id : String?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("poster_path")
    val poster : String?,

    @SerializedName("backdrop_path")
    val backdrop : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("release_date")
    val realese : String?,

    @SerializedName("vote_average")
    val voteAverage : String?


) : Parcelable{


    constructor() : this("", "", "", "", "", "", "")
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        movieId = this.id.toString(),
        title = this.title.toString(),
        poster = this.poster.toString(),
        backdrop = this.backdrop.toString(),
        overview = this.overview.toString(),
        realese = this.realese.toString(),
        voteAverage = this.voteAverage.toString()
    )

}
