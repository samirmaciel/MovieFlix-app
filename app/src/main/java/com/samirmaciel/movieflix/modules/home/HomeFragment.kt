package com.samirmaciel.movieflix.modules.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.shared.common.MoviesRecyclerAdapter
import com.samirmaciel.movieflix.shared.repository.MovieRepositoryInterface
import com.samirmaciel.movieflix.shared.data.MovieApiService


class HomeFragment : Fragment() {

    private lateinit var popularAdapter : MoviesRecyclerAdapter
    private lateinit var topratedAdapter : MoviesRecyclerAdapter
    private lateinit var mPopularRecycler : RecyclerView
    private lateinit var mToprateRecycler : RecyclerView
    private lateinit var mHorrorRecycler : RecyclerView

    private val viewModel : HomeViewModel by activityViewModels {
        HomeViewModel.HomeViewModelFactory(
            MovieApiService.getInstance().create(MovieRepositoryInterface::class.java)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        mPopularRecycler = view.findViewById(R.id.popularRecycler)
        mToprateRecycler = view.findViewById(R.id.topraredRecycler)
        mHorrorRecycler = view.findViewById(R.id.horrorRecycler)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()


    }

    override fun onStart() {
        super.onStart()

        viewModel.popularList.observe(this) { list ->
            popularAdapter.list = list
            popularAdapter.notifyDataSetChanged()
        }

        viewModel.topratedList.observe(this) {  list ->
            topratedAdapter.list = list
            topratedAdapter.notifyDataSetChanged()
        }

    }

    private fun initRecycler() {
        topratedAdapter = MoviesRecyclerAdapter {

        }
        mToprateRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topratedAdapter
        }
        popularAdapter = MoviesRecyclerAdapter{

        }
        mPopularRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mPopularRecycler.adapter = popularAdapter
    }

}