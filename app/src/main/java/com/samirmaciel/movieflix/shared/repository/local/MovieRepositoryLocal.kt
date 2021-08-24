package com.samirmaciel.movieflix.shared.repository.local

import com.samirmaciel.movieflix.shared.dao.MovieDao
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.toMovieEntityLocal
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal

class MovieRepositoryLocal(private val movieDao : MovieDao) {

    suspend fun save(movieEntityApi : MovieEntityApi){
        val movieEntity = movieEntityApi.toMovieEntityLocal()
        movieDao.save(movieEntity)
    }

    suspend fun deleteById(movieId: String){
        movieDao.deleteById(movieId)
    }

    suspend fun findAll() : MutableList<MovieEntityLocal>{
        return movieDao.findAll()
    }

    suspend fun findById(movieId : String) : MovieEntityLocal{
        return movieDao.findById(movieId)
    }

}