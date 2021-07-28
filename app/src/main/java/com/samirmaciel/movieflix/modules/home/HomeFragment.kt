package com.samirmaciel.movieflix.modules.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentHomeBinding
import com.samirmaciel.movieflix.shared.common.MoviesRecyclerAdapter
import com.samirmaciel.movieflix.shared.model.MovieApiInterface
import com.samirmaciel.movieflix.shared.model.MovieApiService
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {

    private lateinit var popularAdapter : MoviesRecyclerAdapter
    private lateinit var topratedAdapter : MoviesRecyclerAdapter
    private lateinit var mPopularRecycler : RecyclerView
    private lateinit var mToprateRecycler : RecyclerView
    private lateinit var mFrontImage : ImageView
    private lateinit var mFrontTitle : TextView

    private val viewModel : HomeViewModel by activityViewModels {
        HomeViewModel.HomeViewModelFactory(
            MovieApiService.getInstance().create(MovieApiInterface::class.java)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var binding : FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        mPopularRecycler = view.findViewById(R.id.popularRecycler)
        mToprateRecycler = view.findViewById(R.id.topraredRecycler)
        mFrontImage = view.findViewById(R.id.frontImage)
        mFrontTitle = view.findViewById(R.id.textMovieName)
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
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + list[0].poster.toString()).into(mFrontImage)
            mFrontTitle.text = list[0].title.toString()
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