package com.samirmaciel.movieflix.modules.bottomsheetdetail_page

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
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
import com.samirmaciel.movieflix.shared.model.api.toMovieWatchLaterEntityLocal
import com.samirmaciel.movieflix.shared.model.api.toMovieWatchedEntityLocal
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchLaterRepositoryLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchedRepositoryLocal
import com.squareup.picasso.Picasso

class BottomSheetDetail : BottomSheetDialogFragment() {

    private var haveMovieOnWatched : Boolean = false
    private var haveMovieOnListPlayLater = false
    private lateinit var movieReturn : MovieEntityApi
    private lateinit var actorsRecyclerAdapter : ActorsRecyclerAdapter

    private val viewModel : BottomSheetViewModel by activityViewModels {
        BottomSheetViewModel.BottomSheetViewModelFactory(
            MovieWatchedRepositoryLocal(AppDatabase.getDatabase(requireContext()).MovieWatchedDao()),
            MovieWatchLaterRepositoryLocal(AppDatabase.getDatabase(requireContext()).MovieWatchLaterDao()),
            MovieApiService.getInstance().create(MovieRepositoryApiInterface::class.java)
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

        val fragmentCall : Boolean = requireArguments().getBoolean("mylistCall", false)

        if(fragmentCall){
            binding.buttonAddToPlayLater.isEnabled = false
            binding.buttonAddToPlayLater.visibility = View.GONE
            binding.buttonWatchedMovie.visibility = View.GONE
            binding.buttonWatchedMovie.isEnabled = false
        }
        movieReturn = getMovieReturn()
        viewModel.findByIdOnWatched(movieReturn.toMovieWatchedEntityLocal().movieId)
        viewModel.findByIdOnWatchlater(movieReturn.toMovieWatchLaterEntityLocal().movieId)
        viewModel.getActorsOfMovie(movieReturn.toMovieWatchedEntityLocal().movieId)

        checkWatchedMovie()
        checkWatchLaterMovie()

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

        binding.buttonAddToPlayLater.setOnClickListener{
            addOrRemoveWatchLaterMovie()
        }

        binding.buttonWatchedMovie.setOnClickListener{
            addOrRemoveMovieWatched()
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

    private fun changeWatchedMovieCheck(set : Boolean){
        if(set){
            binding.buttonWatchedMovie.apply {
                setImageResource(R.drawable.ic_library_add_check)
                setColorFilter(Color.parseColor("#0BB105"))
            }

        }else{
            binding.buttonWatchedMovie.apply {
                setImageResource(R.drawable.ic_add_box)
                setColorFilter(Color.WHITE)
            }
        }
    }

    private fun changeAddToPlayLaterCheck(set : Boolean){
        if(set){
            binding.buttonAddToPlayLater.apply {
                setImageResource(R.drawable.ic_baseline_playlist_add_check)
                setColorFilter(Color.parseColor("#0BB105"))
            }
        }else{
            binding.buttonAddToPlayLater.apply {
                setImageResource(R.drawable.ic_baseline_playlist_add)
                setColorFilter(Color.WHITE)
            }
        }
    }

    private fun checkWatchLaterMovie(){
        viewModel.movieWatchLaterItem.observe(this){
            if(it != null){
                if(it.movieId.equals(movieReturn.id)){
                    binding.buttonAddToPlayLater.apply {
                        setImageResource(R.drawable.ic_baseline_playlist_add_check)
                        setColorFilter(Color.parseColor("#0BB105"))
                    }
                    haveMovieOnListPlayLater = true
                }
            }
        }
    }

    private fun checkWatchedMovie(){
        viewModel.movieWatchedItem.observe(this){

            if(it != null){
                if(it.movieId.equals(movieReturn.id)){
                    binding.buttonWatchedMovie.apply {
                        setImageResource(R.drawable.ic_library_add_check)
                        setColorFilter(Color.parseColor("#0BB105"))
                    }

                    binding.buttonAddToPlayLater.apply {
                        isEnabled = false
                        setColorFilter(Color.GRAY)
                    }
                    haveMovieOnWatched = true
                }
            }
        }
    }

    private fun addOrRemoveMovieWatched(){
        if (!haveMovieOnWatched){
            changeWatchedMovieCheck(true)
            haveMovieOnWatched = true
            binding.buttonAddToPlayLater.apply {
                setImageResource(R.drawable.ic_baseline_playlist_add)
                isEnabled = false
                setColorFilter(Color.GRAY)
            }

            Toast.makeText(requireContext(), "Filme adicionado na minha lista", Toast.LENGTH_LONG).show()
            viewModel.deleteMovieOnWatchLater(movieReturn.toMovieWatchLaterEntityLocal().movieId)
            viewModel.saveMovieOnWatched(movieReturn)
        }else{
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle("Deseja remover este filme da sua lista?")
                setPositiveButton("Sim", DialogInterface.OnClickListener{ dialog, id ->
                    changeWatchedMovieCheck(false)
                    haveMovieOnListPlayLater = false
                    binding.buttonAddToPlayLater.apply {
                        setImageResource(R.drawable.ic_baseline_playlist_add)
                        isEnabled = true
                        setColorFilter(Color.WHITE)
                    }

                    haveMovieOnWatched = false
                    Toast.makeText(requireContext(), "Filme removido da minha lista", Toast.LENGTH_LONG).show()
                    viewModel.deleteMovieOnWatched(movieReturn.toMovieWatchedEntityLocal().movieId)
                    viewModel.movieWatchedItem.postValue(null)
                })
                setNegativeButton("Não", null)
            }
            alert.create().show()
        }
    }

    private fun addOrRemoveWatchLaterMovie(){
        if(!haveMovieOnListPlayLater){
            changeAddToPlayLaterCheck(true)
            haveMovieOnListPlayLater = true
            Toast.makeText(requireContext(), "Filme adicionado ao assistir mais tarde", Toast.LENGTH_SHORT).show()
            viewModel.saveMovieOnWatchLater(movieReturn)
        }else{
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle("Deseja remover este filme da lista assistir mais tarde?")
                setPositiveButton("Sim", DialogInterface.OnClickListener{
                        dialog, id ->
                    changeAddToPlayLaterCheck(false)
                    haveMovieOnListPlayLater = false
                    Toast.makeText(requireContext(), "Filme removido da lista assistir mais tarde", Toast.LENGTH_SHORT).show()
                    viewModel.deleteMovieOnWatchLater(movieReturn.toMovieWatchLaterEntityLocal().movieId)
                    viewModel.movieWatchLaterItem.postValue(null)
                })
                setNegativeButton("Não", null)
            }
            alert.create().show()
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