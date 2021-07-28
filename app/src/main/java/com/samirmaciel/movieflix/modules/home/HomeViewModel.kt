package com.samirmaciel.movieflix.modules.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.Movie
import com.samirmaciel.movieflix.shared.model.MovieApiInterface
import com.samirmaciel.movieflix.shared.model.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val movieApi : MovieApiInterface) : ViewModel() {

    val popularList = MutableLiveData<MutableList<Movie>>()
    val topratedList = MutableLiveData<MutableList<Movie>>()

    init {
        updatePopularList()
        updateTopratedList()
    }

    fun updatePopularList(){
        viewModelScope.launch {
            movieApi.getPopularMovies().enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    popularList.postValue(response.body()?.movies)
                }
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                }
            })
        }
    }

    fun updateTopratedList(){
        viewModelScope.launch {
            movieApi.getRatedMovies().enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    topratedList.postValue(response.body()?.movies)
                }
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("ERROR", "onFailure: " + t.message.toString())
                }
            })
        }
    }


    class HomeViewModelFactory(private val movieApi: MovieApiInterface) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(movieApi) as T
        }


    }
}