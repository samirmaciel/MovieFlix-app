package com.samirmaciel.movieflix.shared.repository.local

import com.samirmaciel.movieflix.shared.dao.MovieDao
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.toMovieEntity
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal

class MovieRepositoryLocal(private val movieDao : MovieDao) {

    suspend fun save(movieEntityApi : MovieEntityApi){
        val movieEntity = movieEntityApi.toMovieEntity()
        movieDao.save(movieEntity)
    }

    suspend fun deleteById(id : Long){
        movieDao.deleteById(id)
    }

    suspend fun findAll() : MutableList<MovieEntityLocal>{
        return movieDao.findAll()
    }

}