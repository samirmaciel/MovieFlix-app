package com.samirmaciel.movieflix.modules.detailmovie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.DetailMovieFragmentBinding
import com.squareup.picasso.Picasso

class DetailMovieFragment : Fragment(R.layout.detail_movie_fragment) {

    private lateinit var binding : DetailMovieFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DetailMovieFragmentBinding.bind(view)

        val url = arguments?.get("imageURL").toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + url).into(binding.imageRecyclerPoster)

        binding.textOLA.text = "OLA MUNDO"



        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

    }


}