package com.samirmaciel.movieflix.shared.repository.api

import com.samirmaciel.movieflix.shared.model.api.CastResponse
import com.samirmaciel.movieflix.shared.model.api.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieRepositoryApiInterface{

    @GET("/3/movie/popular?api_key=$APIKEY&$LANGUAGE&$IMAGELANGUAGE,null")
    fun  getPopularMovies() : Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=$APIKEY&$LANGUAGE&$IMAGELANGUAGE,null")
    fun getRatedMovies() : Call<MovieResponse>

    @GET("/3/movie/upcoming?api_key=$APIKEY&$LANGUAGE&$IMAGELANGUAGE,null")
    fun getUpcomingMovies() : Call<MovieResponse>

    @GET("/3/search/movie?api_key=$APIKEY&$LANGUAGE&$IMAGELANGUAGE&page=1&include_adult=false")
    fun getSearchMovies(@Query("query") text : String) : Call<MovieResponse>

    @GET("/3/movie/{movieId}/credits?api_key=$APIKEY&$LANGUAGE")
    fun getCastOfMovie(@Path("movieId") id : String) : Call<CastResponse>

}