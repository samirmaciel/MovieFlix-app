package com.samirmaciel.movieflix.shared.repository

import com.samirmaciel.movieflix.shared.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET


interface MovieRepositoryInterface{

    @GET("/3/movie/popular?api_key=b2b2968f90966aca368661a132319376")
    fun  getPopularMovies() : Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=b2b2968f90966aca368661a132319376")
    fun getRatedMovies() : Call<MovieResponse>

}