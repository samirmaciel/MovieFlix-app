package com.samirmaciel.movieflix.shared.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal

@Dao
interface MovieDao {

    @Query("SELECT * FROM moviesTable")
    suspend fun findAll(): MutableList<MovieEntityLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(movieEntityLocal : MovieEntityLocal)

    @Query("DELETE FROM moviesTable WHERE id = :id")
    suspend fun deleteById(id : Long)

    @Query("SELECT * FROM moviesTable WHERE id = :id")
    suspend fun findById(id : Long)

}

