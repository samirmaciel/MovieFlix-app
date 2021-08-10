package com.samirmaciel.movieflix.modules.bottomsheetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samirmaciel.movieflix.shared.apidata.MovieApiService

class BottomSheetViewModel(private val repository : MovieApiService) : ViewModel() {









    class BottomSheetViewModelFactory(private val repository: MovieApiService) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BottomSheetViewModel(repository) as T
        }

    }
}