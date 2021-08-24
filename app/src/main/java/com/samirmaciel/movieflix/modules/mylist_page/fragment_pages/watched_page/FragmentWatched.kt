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
import com.samirmaciel.movieflix.modules.mylist_page.MylistViewModel
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapterMylist
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal

class FragmentWatched : Fragment(R.layout.fragment_watched) {

    private var _binding : FragmentWatchedBinding? = null
    private val binding : FragmentWatchedBinding get() = _binding!!
    private lateinit var adapterRecyclerView : MoviesRecyclerAdapterMylist

    private val viewModel : MylistViewModel by activityViewModels {
        MylistViewModel.MylistViewModelFactory(
            MovieRepositoryLocal(
                AppDatabase.getDatabase(
                    requireContext()
                ).MovieDao()
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

        viewModel.movieList.observe(this){
            adapterRecyclerView.list = it
            adapterRecyclerView.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(){
        adapterRecyclerView = MoviesRecyclerAdapterMylist({ position, movie ->
            val alert = AlertDialog.Builder(requireContext()).apply {
                setTitle("Tem certeza que deseja remover este filme?")
                setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
                    viewModel.deleteById(movie.movieId)
                    adapterRecyclerView.list.remove(movie)
                    adapterRecyclerView.notifyItemRemoved(position)
                })
                setNegativeButton("Não", null)
            }.create().show()
        }, {

        })

        binding.watchedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterRecyclerView
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}