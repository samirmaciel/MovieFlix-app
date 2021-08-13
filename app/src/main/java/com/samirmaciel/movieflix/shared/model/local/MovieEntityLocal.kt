package com.samirmaciel.movieflix.shared.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi

@Entity
data class MovieEntityLocal(

    @PrimaryKey val movieId : String,
    @ColumnInfo val title : String,
    @ColumnInfo val poster : String,
    @ColumnInfo val backdrop : String,
    @ColumnInfo val overview : String,
    @ColumnInfo val realese : String,
    @ColumnInfo val voteAverage : String
)
