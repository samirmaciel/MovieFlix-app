package com.samirmaciel.movieflix.modules.bottomsheetdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentBottomsheetdetailBinding
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.squareup.picasso.Picasso

class BottomSheetDetail : BottomSheetDialogFragment() {

    private var _binding : FragmentBottomsheetdetailBinding? = null
    private val binding : FragmentBottomsheetdetailBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottomsheetdetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBottomsheetdetailBinding.bind(view)


        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //setExpandedOffset(offsetFromTop)
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }



    override fun onStart() {
        super.onStart()

        val movie = getMovieParms()

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.backdrop.toString()).into(binding.imagePosterMovie)
        binding.movieTitle.text = movie.title.toString()
        binding.movieOverview.text = movie.overview.toString()
        binding.movieVoteaverage.text = movie.voteAverage.toString()

    }

    private fun getMovieParms() : MovieEntityApi {
        return MovieEntityApi(null, arguments?.getString("title"), null, arguments?.getString("backdrop"), arguments?.getString("overview"), null, arguments?.getString("voteAverage"))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}