package com.samirmaciel.movieflix.modules.bottomsheetdetail_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.api.ActorModel
import com.samirmaciel.movieflix.shared.model.api.CastResponse
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetViewModel(private val repositoryLocal : MovieRepositoryLocal, private val repositoryApi: MovieRepositoryApiInterface) : ViewModel() {

    var movieEntityApiList : MutableLiveData<MutableList<MovieEntityLocal>> = MutableLiveData()
    var movieItemList : MutableLiveData<MovieEntityLocal> = MutableLiveData()
    var castMovieList : MutableLiveData<MutableList<ActorModel>> = MutableLiveData()

    fun getAllMovies(){
        viewModelScope.launch {
            movieEntityApiList.postValue(repositoryLocal.findAll())
        }
    }

    fun saveMovie(movie : MovieEntityApi){
        viewModelScope.launch {
            repositoryLocal.save(movie)
        }
    }

    fun findById(id : String){
        viewModelScope.launch {
            movieItemList.postValue(repositoryLocal.findById(id))

        }

    }

    fun deleteMovie(movieId : String){
        viewModelScope.launch {
            repositoryLocal.deleteById(movieId)
        }
    }

    fun getActorsOfMovie(movieId : String){
        viewModelScope.launch {
            repositoryApi.getCastOfMovie(movieId).enqueue(object : Callback<CastResponse>{
                override fun onResponse(
                    call: Call<CastResponse>,
                    response: Response<CastResponse>
                ) {
                    castMovieList.postValue(response.body()?.castList)
                }

                override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }



    class BottomSheetViewModelFactory(private val repositoryLocal: MovieRepositoryLocal, private val repositoryApi: MovieRepositoryApiInterface) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BottomSheetViewModel(repositoryLocal, repositoryApi) as T
        }

    }
}