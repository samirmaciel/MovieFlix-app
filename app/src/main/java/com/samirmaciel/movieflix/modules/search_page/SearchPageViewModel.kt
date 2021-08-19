package com.samirmaciel.movieflix.modules.search_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.MovieResponse
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPageViewModel(private val repository : MovieRepositoryApiInterface) : ViewModel() {

    var movieList : MutableLiveData<MutableList<MovieEntityApi>> = MutableLiveData()


    fun searchMovie(text : String){
        viewModelScope.launch {
            repository.getSearchMovies(text).enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    movieList.postValue(response.body()?.movieList)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    val list : MutableList<MovieEntityApi> = ArrayList()
                    movieList.postValue(list)
                }

            })
        }
    }




    class SearchPageViewModelFactory(private val repository: MovieRepositoryApiInterface) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchPageViewModel(repository) as T
        }
    }
}