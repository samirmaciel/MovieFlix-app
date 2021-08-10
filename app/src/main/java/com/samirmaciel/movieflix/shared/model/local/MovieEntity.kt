package com.samirmaciel.movieflix.shared.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "moviesTable")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,

    @ColumnInfo
    val movieId : String,

    @ColumnInfo
    val title : String,

    @ColumnInfo
    val poster : String,

    @ColumnInfo
    val backdrop : String,

    @ColumnInfo
    val overview : String,

    @ColumnInfo
    val realese : String,

    @ColumnInfo
    val voteAverage : String


)
