package com.samirmaciel.movieflix.shared.adapter

import android.widget.ImageView
import com.samirmaciel.movieflix.shared.model.Movie

interface OnClickMovie {

    fun onClickMovie(movie: Movie, imageview : ImageView)
}