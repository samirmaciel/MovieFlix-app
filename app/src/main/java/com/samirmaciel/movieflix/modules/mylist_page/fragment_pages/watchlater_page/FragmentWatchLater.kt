package com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watchlater_page

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentWatchLaterBinding
import com.samirmaciel.movieflix.shared.adapter.MoviesWatchLaterRecyclerAdapter
import com.samirmaciel.movieflix.shared.adapter.MoviesWatchedRecyclerAdapter
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchLaterRepositoryLocal


class FragmentWatchLater : Fragment(R.layout.fragment_watch_later) {

    private var _binding : FragmentWatchLaterBinding? = null
    private val binding : FragmentWatchLaterBinding get() = _binding!!
    private lateinit var adapterWatchedRecyclerView : MoviesWatchLaterRecyclerAdapter

    private val viewModel : WatchLaterViewModel by activityViewModels {

        WatchLaterViewModel.WatchLaterViewModelFactory(
            MovieWatchLaterRepositoryLocal(
                AppDatabase.getDatabase(requireContext()).MovieWatchLaterDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWatchLaterBinding.bind(view)
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
        adapterWatchedRecyclerView = MoviesWatchLaterRecyclerAdapter({ position, movie ->
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

        binding.watchLaterRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterWatchedRecyclerView
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}