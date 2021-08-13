package com.samirmaciel.movieflix.modules.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentMylistBinding
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapterApi
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapterMylist
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal

class FragmentMylist : Fragment(R.layout.fragment_mylist) {

    private var _binding : FragmentMylistBinding? = null
    private val binding : FragmentMylistBinding get() = _binding!!
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
        _binding = FragmentMylistBinding.bind(view)
        initRecyclerView()


    }

    private fun initRecyclerView(){
        adapterRecyclerView = MoviesRecyclerAdapterMylist{}
        binding.mylistRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterRecyclerView
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.movieList.observe(this){
            adapterRecyclerView
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}