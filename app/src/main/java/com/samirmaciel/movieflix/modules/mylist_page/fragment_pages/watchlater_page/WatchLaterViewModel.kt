package com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watchlater_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.local.MovieWatchLaterEntityLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchLaterRepositoryLocal
import kotlinx.coroutines.launch

class WatchLaterViewModel(private val repository : MovieWatchLaterRepositoryLocal) : ViewModel() {

    var movieList : MutableLiveData<MutableList<MovieWatchLaterEntityLocal>> = MutableLiveData()

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
           movieList.postValue(repository.findAll())
        }

    }

    fun deleteById(movieId : String){
        viewModelScope.launch {
            repository.deleteById(movieId)
        }
    }


    class WatchLaterViewModelFactory(private val repository: MovieWatchLaterRepositoryLocal) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WatchLaterViewModel(repository) as T
        }


    }
}