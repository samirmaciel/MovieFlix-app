package com.samirmaciel.movieflix.modules.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.repository.MovieRepositoryInterface
import com.samirmaciel.movieflix.shared.model.Movie
import com.samirmaciel.movieflix.shared.model.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: MovieRepositoryInterface) : ViewModel() {

    val popularList = MutableLiveData<MutableList<Movie>>()
    val topratedList = MutableLiveData<MutableList<Movie>>()

    init {
        updatePopularList()
        updateTopratedList()
    }

    fun updatePopularList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies().enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    popularList.postValue(response.body()?.movies)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("POPULARERROR", "onFailure: " + t.message.toString())
                }
            })
        }
    }


    fun updateTopratedList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRatedMovies().enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    topratedList.postValue(response.body()?.movies)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("TOPRATEDERROR", "onFailure: " + t.message.toString())
                }
            })
        }
    }


    class HomeViewModelFactory(private val repository: MovieRepositoryInterface) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}