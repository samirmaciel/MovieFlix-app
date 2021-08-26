package com.samirmaciel.movieflix.modules.bottomsheetdetail_page

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentBottomsheetdetailBinding
import com.samirmaciel.movieflix.shared.adapter.ActorsRecyclerAdapter
import com.samirmaciel.movieflix.shared.apidata.MovieApiService
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.model.api.ActorModel
import com.samirmaciel.movieflix.shared.model.api.MovieEntityApi
import com.samirmaciel.movieflix.shared.model.api.toMovieEntityLocal
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal
import com.squareup.picasso.Picasso
import retrofit2.create

class BottomSheetDetail : BottomSheetDialogFragment() {

    private var haveMovieOnMyList : Boolean = false
    private lateinit var movieReturn : MovieEntityApi
    private lateinit var actorsRecyclerAdapter : ActorsRecyclerAdapter

    private val viewModel : BottomSheetViewModel by activityViewModels {
        BottomSheetViewModel.BottomSheetViewModelFactory(
            MovieRepositoryLocal(AppDatabase.getDatabase(requireContext()).MovieDao()), MovieApiService.getInstance().create(MovieRepositoryApiInterface::class.java)
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
        initRecycler()
       //expandBottomSheet()
    }

    override fun onStart() {
        super.onStart()

        movieReturn = getMovieReturn()
        viewModel.findById(movieReturn.toMovieEntityLocal().movieId)
        viewModel.getActorsOfMovie(movieReturn.toMovieEntityLocal().movieId)

        checkMovieOnMyList()

        bindMovieOnLayout(movieReturn)

        viewModel.castMovieList.observe(this){
            var actorsList : MutableList<ActorModel> = arrayListOf()
            for(actor in it){
                if(actor.known_for_department.equals("Acting") && actor.profile_path != null){
                    actorsList.add(actor)
                }
            }
            actorsRecyclerAdapter.list = actorsList
            actorsRecyclerAdapter.notifyDataSetChanged()
        }

        binding.buttonAddOrRemove.setOnClickListener{
            if (!haveMovieOnMyList){
                changeAddOrRemoveIcon(true)
                haveMovieOnMyList = true
                Toast.makeText(requireContext(), "Filme adicionado na minha lista", Toast.LENGTH_LONG).show()
                viewModel.saveMovie(movieReturn)
            }else{
                val alert = AlertDialog.Builder(requireContext()).apply {
                    setTitle("Deseja remover este filme da sua lista?")
                    setPositiveButton("Sim", DialogInterface.OnClickListener{ dialog, id ->
                        changeAddOrRemoveIcon(false)
                        haveMovieOnMyList = false
                        Toast.makeText(requireContext(), "Filme removido da minha lista", Toast.LENGTH_LONG).show()
                        viewModel.deleteMovie(movieReturn.toMovieEntityLocal().movieId)
                        viewModel.movieItemList.postValue(null)
                    })
                    setNegativeButton("Não", null)
                }
                alert.create().show()
            }
        }
    }

    private fun initRecycler(){
        actorsRecyclerAdapter = ActorsRecyclerAdapter{}

        val actorsRecyclerView = binding.castRecyclerView.apply {
            adapter = actorsRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }



    private fun getMovieReturn() : MovieEntityApi {
        return MovieEntityApi(arguments?.getString("movieId"), arguments?.getString("title"), arguments?.getString("poster"), arguments?.getString("backdrop"), arguments?.getString("overview"), arguments?.getString("realese"), arguments?.getString("voteAverage"))
    }

    private fun bindMovieOnLayout(movie : MovieEntityApi) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.backdrop.toString())
            .into(binding.imagePosterMovie)


        binding.movieTitle.text = movie.title.toString()
        binding.movieOverview.text = movie.overview.toString()
        binding.movieVoteaverage.text = movie.voteAverage.toString()
        binding.releaseDateText.text = movie.realese!!.split("-")[0].toString()

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun changeAddOrRemoveIcon(set : Boolean){
        if(set){
            binding.buttonAddOrRemove.setImageResource(R.drawable.ic_library_add_check)
        }else{
            binding.buttonAddOrRemove.setImageResource(R.drawable.ic_add_box)
        }
    }

    private fun checkMovieOnMyList(){
        viewModel.movieItemList.observe(this){

            if(it != null){
                if(it.title.equals(movieReturn.title)){
                    Toast.makeText(requireContext(), "ObserveMovie", Toast.LENGTH_LONG).show()
                    binding.buttonAddOrRemove.setImageResource(R.drawable.ic_library_add_check)
                    haveMovieOnMyList = true
                }
            }
        }
    }

    private fun expandBottomSheet(){
        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //setExpandedOffset(offsetFromTop)
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}