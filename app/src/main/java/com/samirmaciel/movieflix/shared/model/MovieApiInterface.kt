package com.samirmaciel.movieflix.shared.model

import retrofit2.Call
import retrofit2.http.GET


interface MovieApiInterface{

    @GET("/3/movie/popular?api_key=b2b2968f90966aca368661a132319376")
    fun  getMovies() : Call<MovieResponse>

}