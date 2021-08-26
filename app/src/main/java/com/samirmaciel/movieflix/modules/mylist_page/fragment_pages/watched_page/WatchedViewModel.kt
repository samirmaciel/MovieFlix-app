package com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watched_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.local.MovieWatchedEntityLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchedRepositoryLocal
import kotlinx.coroutines.launch

class WatchedViewModel(private val repository : MovieWatchedRepositoryLocal) : ViewModel() {

    var movieList : MutableLiveData<MutableList<MovieWatchedEntityLocal>> = MutableLiveData()


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


    class WatchedViewModelFactory(private val repository : MovieWatchedRepositoryLocal) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WatchedViewModel(repository) as T
        }


    }
}