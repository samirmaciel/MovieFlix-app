package com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watched_page

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentWatchedBinding
import com.samirmaciel.movieflix.shared.adapter.MoviesWatchedRecyclerAdapter
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchedRepositoryLocal

class FragmentWatched : Fragment(R.layout.fragment_watched) {

    private var _binding : FragmentWatchedBinding? = null
    private val binding : FragmentWatchedBinding get() = _binding!!
    private lateinit var adapterWatchedRecyclerView : MoviesWatchedRecyclerAdapter

    private val viewModel : WatchedViewModel by activityViewModels {
        WatchedViewModel.WatchedViewModelFactory(
            MovieWatchedRepositoryLocal(
                AppDatabase.getDatabase(requireContext()).MovieWatchedDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWatchedBinding.bind(view)
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        viewModel.getAllMovies()
        viewModel.movieList.observe(this){
            adapterWatchedRecyclerView.list = it
            adapterWatchedRecyclerView.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(){
        adapterWatchedRecyclerView = MoviesWatchedRecyclerAdapter({ position, movie ->
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle("Tem certeza que deseja remover este filme?")
                setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
                    viewModel.deleteById(movie.movieId)
                    adapterWatchedRecyclerView.list.remove(movie)
                    adapterWatchedRecyclerView.notifyItemRemoved(position)
                })
                setNegativeButton("Não", null)
            }.create().show()
        }, {

        })

        binding.watchedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterWatchedRecyclerView
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}