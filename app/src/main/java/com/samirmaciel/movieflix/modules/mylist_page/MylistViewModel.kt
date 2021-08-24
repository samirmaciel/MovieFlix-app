package com.samirmaciel.movieflix.modules.mylist_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal
import kotlinx.coroutines.launch

class MylistViewModel(private val repository : MovieRepositoryLocal) : ViewModel() {

    var movieList : MutableLiveData<MutableList<MovieEntityLocal>> = MutableLiveData()
    var movieItem : MutableLiveData<MovieEntityLocal> = MutableLiveData()

    init {
        getAllMovies()
    }

    fun getAllMovies(){
        viewModelScope.launch {
            movieList.postValue(repository.findAll())
        }
    }

    fun deleteById(movieId : String){
        viewModelScope.launch {
            repository.deleteById(movieId)
        }
    }

    fun findById(movieId : String){
        viewModelScope.launch {
            movieItem.postValue(repository.findById(movieId))
        }
    }

    class MylistViewModelFactory(private val repository: MovieRepositoryLocal) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MylistViewModel(repository) as T
        }
    }
}