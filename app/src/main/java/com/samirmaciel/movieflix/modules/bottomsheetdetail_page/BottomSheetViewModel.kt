package com.samirmaciel.movieflix.modules.bottomsheetdetail_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samirmaciel.movieflix.shared.model.api.ActorModel
import com.samirmaciel.movieflix.shared.model.api.CastResponse
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.local.MovieWatchedEntityLocal
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchedRepositoryLocal
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetViewModel(private val watchedRepositoryLocal : MovieWatchedRepositoryLocal, private val repositoryApi: MovieRepositoryApiInterface) : ViewModel() {

    var movieWatchedEntityApiList : MutableLiveData<MutableList<MovieWatchedEntityLocal>> = MutableLiveData()
    var movieWatchedItemList : MutableLiveData<MovieWatchedEntityLocal> = MutableLiveData()
    var castMovieList : MutableLiveData<MutableList<ActorModel>> = MutableLiveData()

    fun getAllMovies(){
        viewModelScope.launch {
            movieWatchedEntityApiList.postValue(watchedRepositoryLocal.findAll())
        }
    }

    fun saveMovie(movie : MovieEntityApi){
        viewModelScope.launch {
            watchedRepositoryLocal.save(movie)
        }
    }

    fun findById(id : String){
        viewModelScope.launch {
            movieWatchedItemList.postValue(watchedRepositoryLocal.findById(id))

        }

    }

    fun deleteMovie(movieId : String){
        viewModelScope.launch {
            watchedRepositoryLocal.deleteById(movieId)
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



    class BottomSheetViewModelFactory(private val watchedRepositoryLocal: MovieWatchedRepositoryLocal, private val repositoryApi: MovieRepositoryApiInterface) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BottomSheetViewModel(watchedRepositoryLocal, repositoryApi) as T
        }

    }
}