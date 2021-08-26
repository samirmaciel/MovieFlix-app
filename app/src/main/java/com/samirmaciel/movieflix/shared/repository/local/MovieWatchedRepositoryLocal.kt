package com.samirmaciel.movieflix.shared.repository.local

import com.samirmaciel.movieflix.shared.dao.MovieWatchedDao
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.toMovieWatchedEntityLocal
import com.samirmaciel.movieflix.shared.model.local.MovieWatchedEntityLocal

class MovieWatchedRepositoryLocal(private val movieWatchedDao : MovieWatchedDao) {

    suspend fun save(movieEntityApi : MovieEntityApi){
        val movieEntity = movieEntityApi.toMovieWatchedEntityLocal()
        movieWatchedDao.save(movieEntity)
    }

    suspend fun deleteById(movieId: String){
        movieWatchedDao.deleteById(movieId)
    }

    suspend fun findAll() : MutableList<MovieWatchedEntityLocal>{
        return movieWatchedDao.findAll()
    }

    suspend fun findById(movieId : String) : MovieWatchedEntityLocal{
        return movieWatchedDao.findById(movieId)
    }

}