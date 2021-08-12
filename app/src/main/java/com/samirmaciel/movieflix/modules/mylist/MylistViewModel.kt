package com.samirmaciel.movieflix.modules.mylist

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


    suspend fun getAllMovies(){
        viewModelScope.launch {
            movieList.postValue(repository.findAll())
        }
    }

    suspend fun deleteById(id : Long){
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    suspend fun findById(id : Long){
        viewModelScope.launch {
            movieItem.postValue(repository.findById(id))
        }
    }


    class MylistViewModelFactory(private val repository: MovieRepositoryLocal) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MylistViewModel(repository) as T
        }


    }
}