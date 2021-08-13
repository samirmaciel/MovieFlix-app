package com.samirmaciel.movieflix.shared.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntityLocal")
    suspend fun findAll(): MutableList<MovieEntityLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(movieEntityLocal : MovieEntityLocal)

    @Query("DELETE FROM MovieEntityLocal WHERE id = :movieId")
    suspend fun deleteById(movieId : String)

    @Query("SELECT * FROM MovieEntityLocal WHERE movieId = :movieId")
    suspend fun findById(movieId : String) : MovieEntityLocal

}

