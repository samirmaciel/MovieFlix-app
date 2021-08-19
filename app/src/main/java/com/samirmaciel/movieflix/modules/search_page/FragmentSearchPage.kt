package com.samirmaciel.movieflix.modules.search_page

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentSearchBinding
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapterApi
import com.samirmaciel.movieflix.shared.apidata.MovieApiService
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface

class FragmentSearchPage : Fragment(R.layout.fragment_search){

    private var _binding : FragmentSearchBinding? = null
    private val binding : FragmentSearchBinding get() = _binding!!
    private lateinit var recyclerAdapter : MoviesRecyclerAdapterApi

    private val viewModel: SearchPageViewModel by activityViewModels {
        SearchPageViewModel.SearchPageViewModelFactory(
            MovieApiService.getInstance().create(MovieRepositoryApiInterface::class.java)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        initRecyclerView()

    }

    override fun onStart() {
        super.onStart()

        binding.inputSearchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchMovie(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        viewModel.movieList.observe(this){
            if(it != null){
                recyclerAdapter.list = it
                recyclerAdapter.notifyDataSetChanged()
            }

        }
    }



    private fun initRecyclerView(){
        recyclerAdapter = MoviesRecyclerAdapterApi {}

        binding.searchRecyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}