package com.samirmaciel.movieflix.shared.repository.api

import com.samirmaciel.movieflix.shared.model.api.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

const val APIKEY = "b2b2968f90966aca368661a132319376"
const val LANGUAGE = "language=pt-BR"
const val IMAGELANGUAGE = "append_to_response=images&include_image_language=pt-BR"

interface MovieRepositoryApiInterface{

    @GET("/3/movie/popular?api_key=$APIKEY&$LANGUAGE&$IMAGELANGUAGE,null")
    fun  getPopularMovies() : Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=$APIKEY&$LANGUAGE&$IMAGELANGUAGE,null")
    fun getRatedMovies() : Call<MovieResponse>

}