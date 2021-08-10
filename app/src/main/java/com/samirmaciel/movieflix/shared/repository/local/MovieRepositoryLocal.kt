package com.samirmaciel.movieflix.shared.repository.local

import com.samirmaciel.movieflix.shared.dao.MovieDao
import com.samirmaciel.movieflix.shared.model.api.Movie
import com.samirmaciel.movieflix.shared.model.api.toMovieEntity
import com.samirmaciel.movieflix.shared.model.local.MovieEntity

class MovieRepositoryLocal(private val movieDao : MovieDao) {

    suspend fun save(movie : Movie){
        val movieEntity = movie.toMovieEntity()
        movieDao.save(movieEntity)
    }

    suspend fun deleteById(id : Long){
        movieDao.deleteById(id)
    }

    suspend fun findAll() : MutableList<MovieEntity>{
        return movieDao.findAll()
    }

}