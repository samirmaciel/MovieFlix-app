package com.samirmaciel.movieflix.modules.bottomsheetdetail_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val repository : MovieRepositoryLocal) : ViewModel() {

    var movieEntityApiList : MutableLiveData<MutableList<MovieEntityLocal>> = MutableLiveData()
    var movieItem : MutableLiveData<MovieEntityLocal> = MutableLiveData()

    fun getAllMovies(){
        viewModelScope.launch {
            movieEntityApiList.postValue(repository.findAll())
        }
    }

    fun saveMovie(movie : MovieEntityApi){
        viewModelScope.launch {
            repository.save(movie)
        }
    }

    fun findById(id : String){
        viewModelScope.launch {
            movieItem.postValue(repository.findById(id))

        }

    }

    fun deleteMovie(movieId : String){
        viewModelScope.launch {
            repository.deleteById(movieId)
        }
    }



    class BottomSheetViewModelFactory(private val repository: MovieRepositoryLocal) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BottomSheetViewModel(repository) as T
        }

    }
}