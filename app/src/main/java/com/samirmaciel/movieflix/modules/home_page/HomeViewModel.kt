package com.samirmaciel.movieflix.modules.home_page

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: MovieRepositoryApiInterface) : ViewModel() {

    val popularList = MutableLiveData<MutableList<MovieEntityApi>>()
    val topratedList = MutableLiveData<MutableList<MovieEntityApi>>()
    val upcomingList = MutableLiveData<MutableList<MovieEntityApi>>()

    init {
        updatePopularList()
        updateTopratedList()
        updateUpcomingList()
    }

    fun updatePopularList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies().enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    popularList.postValue(response.body()?.movieList)
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
                    topratedList.postValue(response.body()?.movieList)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    val list : MutableList<MovieEntityApi> = ArrayList()
                    topratedList.postValue(list)
                }
            })
        }
    }

    fun updateUpcomingList(){
        viewModelScope.launch {
            repository.getUpcomingMovies().enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    upcomingList.postValue(response.body()?.movieList)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

                }

            })
        }
    }


    class HomeViewModelFactory(private val repository: MovieRepositoryApiInterface) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}