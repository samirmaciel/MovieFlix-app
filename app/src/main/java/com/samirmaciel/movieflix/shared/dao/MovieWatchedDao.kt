package com.samirmaciel.movieflix.shared.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samirmaciel.movieflix.shared.model.local.MovieWatchedEntityLocal

@Dao
interface MovieWatchedDao {

    @Query("SELECT * FROM MovieWatchedEntityLocal")
    suspend fun findAll(): MutableList<MovieWatchedEntityLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(movieWatchedEntityLocal : MovieWatchedEntityLocal)

    @Query("DELETE FROM MovieWatchedEntityLocal WHERE movieId = :movieId")
    suspend fun deleteById(movieId : String)

    @Query("SELECT * FROM MovieWatchedEntityLocal WHERE movieId = :movieId")
    suspend fun findById(movieId : String) : MovieWatchedEntityLocal

}

