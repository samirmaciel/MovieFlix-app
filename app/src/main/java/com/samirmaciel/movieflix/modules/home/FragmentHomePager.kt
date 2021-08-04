package com.samirmaciel.movieflix.modules.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentHomeBinding
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapter
import com.samirmaciel.movieflix.shared.adapter.MoviesSliderAdapter
import com.samirmaciel.movieflix.shared.apidata.MovieApiService
import com.samirmaciel.movieflix.shared.repository.MovieRepositoryInterface
import java.util.*

class FragmentHomePager : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!
    private lateinit var mToprateAdapter : MoviesRecyclerAdapter
    private lateinit var mPopularAdapter : MoviesRecyclerAdapter
    private lateinit var mPagerSliderAdapter : MoviesSliderAdapter
    private val timer = Timer()

    private val viewModel : HomeViewModel by activityViewModels {
        HomeViewModel.HomeViewModelFactory(
            MovieApiService.getInstance().create(MovieRepositoryInterface::class.java)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        timer.scheduleAtFixedRate(SliderTimer(), 4000, 6000)
        initViewPager()
        initRecycler()
    }

    override fun onStart() {
        super.onStart()

        viewModel.popularList.observe(this) { list ->
            mPopularAdapter.list = list
            mPopularAdapter.notifyDataSetChanged()
        }

        viewModel.topratedList.observe(this) {  list ->
            mToprateAdapter.list = list
            mPagerSliderAdapter.setListSliders(list)
            mPagerSliderAdapter.notifyDataSetChanged()
            mToprateAdapter.notifyDataSetChanged()
        }

        mToprateAdapter = MoviesRecyclerAdapter {movie, imageview ->
//            val extras = FragmentNavigatorExtras(imageview to imageview.transitionName)
//            val args = Bundle()
//            args.putString("imageURL", movie.poster.toString())
//
//            findNavController().navigate(
//                R.id.action_fragmentHomePager_to_detailMovieFragment,
//                args,
//                null,
//                extras
//            )
        }

    }

    private fun initRecycler() {

        binding.topraredRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mToprateAdapter
        }

        mPopularAdapter = MoviesRecyclerAdapter{movie, imageview ->
            Toast.makeText(requireContext(), "${movie.title}", Toast.LENGTH_LONG).show()
        }

        binding.popularRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularRecycler.adapter = mPopularAdapter
    }

    private fun initViewPager(){
        mPagerSliderAdapter = MoviesSliderAdapter(requireContext())
        binding.viewPagerSlider.adapter = mPagerSliderAdapter
    }

    inner class SliderTimer : TimerTask(){

        override fun run() {
            this@FragmentHomePager.requireActivity().runOnUiThread(object : Runnable{
                override fun run() {
                    if(binding.viewPagerSlider.currentItem < viewModel.topratedList.value!!.size - 1){
                        binding.viewPagerSlider.currentItem = binding.viewPagerSlider.currentItem + 1
                    }else{
                        binding.viewPagerSlider.currentItem = 0
                    }
                }
            })
        }
    }






    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}