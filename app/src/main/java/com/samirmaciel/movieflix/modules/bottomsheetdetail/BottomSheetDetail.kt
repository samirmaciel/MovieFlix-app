package com.samirmaciel.movieflix.modules.bottomsheetdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentBottomsheetdetailBinding
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.toMovieEntityLocal
import com.samirmaciel.movieflix.shared.model.local.MovieEntityLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal
import com.squareup.picasso.Picasso

class BottomSheetDetail : BottomSheetDialogFragment() {

    private var haveMovieOnMyList : Boolean = false
    private lateinit var movieReturn : MovieEntityApi

    private val viewModel : BottomSheetViewModel by activityViewModels {
        BottomSheetViewModel.BottomSheetViewModelFactory(
            MovieRepositoryLocal(AppDatabase.getDatabase(requireContext()).MovieDao())
        )
    }

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

        movieReturn = getMovieReturn()

        viewModel.findById(movieReturn.toMovieEntityLocal().movieId)

        viewModel.movieItem.observe(this){

            if(it != null){
                if(it.title.equals(movieReturn.title)){
                    Log.d("viewModel", "onViewCreated: " + it.title.toString())
                    binding.buttonAddOrRemove.setImageResource(R.drawable.ic_remove_circle)
                    haveMovieOnMyList = true
                }
            }


        }

        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //setExpandedOffset(offsetFromTop)
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }



    override fun onStart() {
        super.onStart()


        bindMovieOnLayout(movieReturn)

        binding.buttonAddOrRemove.setOnClickListener{
            if (haveMovieOnMyList){
                changeAddOrRemoveIcon(false)
                haveMovieOnMyList = false
                viewModel.saveMovie(movieReturn)
            }else{
                changeAddOrRemoveIcon(true)
                haveMovieOnMyList = true
                viewModel.deleteMovie(movieReturn.toMovieEntityLocal().movieId)
            }
        }
    }


    private fun getMovieReturn() : MovieEntityApi {
        return MovieEntityApi(arguments?.getString("movieId"), arguments?.getString("title"), arguments?.getString("poster"), arguments?.getString("backdrop"), arguments?.getString("overview"), arguments?.getString("realese"), arguments?.getString("voteAverage"))
    }

    private fun bindMovieOnLayout(movie : MovieEntityApi){
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.backdrop.toString()).into(binding.imagePosterMovie)
        binding.movieTitle.text = movie.title.toString()
        binding.movieOverview.text = movie.overview.toString()
        binding.movieVoteaverage.text = movie.voteAverage.toString()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun changeAddOrRemoveIcon(set : Boolean){
        if(set){
            binding.buttonAddOrRemove.setImageResource(R.drawable.ic_remove_circle)
        }else{
            binding.buttonAddOrRemove.setImageResource(R.drawable.ic_add_circle)
        }

    }
}