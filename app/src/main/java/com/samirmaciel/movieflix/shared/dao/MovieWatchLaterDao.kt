package com.samirmaciel.movieflix.shared.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samirmaciel.movieflix.shared.model.local.MovieWatchLaterEntityLocal


@Dao
interface MovieWatchLaterDao {

    @Query ("SELECT * FROM MovieWatchLaterEntityLocal")
    suspend fun findAll() : MutableList<MovieWatchLaterEntityLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(movie : MovieWatchLaterEntityLocal)

    @Query ("DELETE FROM MovieWatchLaterEntityLocal WHERE movieId = :movieId")
    suspend fun deleteById(movieId : String)

    @Query ("SELECT * FROM MovieWatchLaterEntityLocal WHERE movieId = :movieId")
    suspend fun findById(movieId : String) : MovieWatchLaterEntityLocal
}