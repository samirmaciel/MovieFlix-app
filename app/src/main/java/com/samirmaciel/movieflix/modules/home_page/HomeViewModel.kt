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
    val slideList = MutableLiveData<MutableList<MovieEntityApi>>()

    var popularPage = 1
    var ratedPage = 1
    var upcomingPage = 1
    var pageLimit = 1000

    init {
        updateSlideList(1)
        updatePopularList(popularPage)
        updateTopratedList(ratedPage)
        updateUpcomingList(upcomingPage)
    }

    fun updatePopularList(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies(page).enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if(popularPage < pageLimit){
                        popularPage++
                        popularList.postValue(response.body()?.movieList)
                    }

                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("POPULARERROR", "onFailure: " + t.message.toString())
                }
            })
        }
    }


    fun updateSlideList(page : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRatedMovies(page).enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    slideList.postValue(response.body()?.movieList)

                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    val list : MutableList<MovieEntityApi> = ArrayList()
                    topratedList.postValue(list)
                }
            })
        }
    }




    fun updateTopratedList(page : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRatedMovies(page).enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if(ratedPage < pageLimit){
                        ratedPage++
                        topratedList.postValue(response.body()?.movieList)
                    }

                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    val list : MutableList<MovieEntityApi> = ArrayList()
                    topratedList.postValue(list)
                }
            })
        }
    }

    fun updateUpcomingList(page : Int){
        viewModelScope.launch {
            repository.getUpcomingMovies(page).enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if(upcomingPage < pageLimit){
                        upcomingPage++
                        upcomingList.postValue(response.body()?.movieList)
                    }

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