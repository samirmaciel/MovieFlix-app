package com.samirmaciel.movieflix.shared.repository.local

import com.samirmaciel.movieflix.shared.dao.MovieWatchLaterDao
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.toMovieWatchLaterEntityLocal
import com.samirmaciel.movieflix.shared.model.local.MovieWatchLaterEntityLocal

class MovieWatchLaterRepositoryLocal(val movieWatchLaterDao : MovieWatchLaterDao ) {

    suspend fun save(movieEntityApi: MovieEntityApi){
        val movieEntity = movieEntityApi.toMovieWatchLaterEntityLocal()
        movieWatchLaterDao.save(movieEntity)
    }

    suspend fun deleteById(movieId : String){
        movieWatchLaterDao.deleteById(movieId)
    }

    suspend fun findAll() : MutableList<MovieWatchLaterEntityLocal>{
        return movieWatchLaterDao.findAll()
    }

    suspend fun findById(movieId : String) : MovieWatchLaterEntityLocal{
        return movieWatchLaterDao.findById(movieId)
    }

}