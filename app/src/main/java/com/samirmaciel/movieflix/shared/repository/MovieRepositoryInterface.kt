package com.samirmaciel.movieflix.shared.repository

import com.samirmaciel.movieflix.shared.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET


interface MovieRepositoryInterface{

    @GET("/3/movie/popular?api_key=b2b2968f90966aca368661a132319376&language=pt-BR&append_to_response=images&include_image_language=pt-BR,null")
    fun  getPopularMovies() : Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=b2b2968f90966aca368661a132319376&language=pt-BR&append_to_response=images&include_image_language=pt-BR,null")
    fun getRatedMovies() : Call<MovieResponse>

}