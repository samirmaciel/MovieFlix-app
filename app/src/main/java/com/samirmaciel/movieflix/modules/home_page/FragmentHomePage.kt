package com.samirmaciel.movieflix.modules.home_page

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentHomeBinding
import com.samirmaciel.movieflix.modules.bottomsheetdetail_page.BottomSheetDetail
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapterApi
import com.samirmaciel.movieflix.shared.adapter.MoviesSliderAdapter
import com.samirmaciel.movieflix.shared.apidata.MovieApiService
import com.samirmaciel.movieflix.shared.repository.api.MovieRepositoryApiInterface
import java.util.*

class FragmentHomePage : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!
    private lateinit var mUpcomingAdapterApi : MoviesRecyclerAdapterApi
    private lateinit var mToprateAdapterApi : MoviesRecyclerAdapterApi
    private lateinit var mPopularAdapterApi : MoviesRecyclerAdapterApi
    private lateinit var mPagerSliderAdapter : MoviesSliderAdapter
    private val timer = Timer()

    private val viewModel : HomeViewModel by activityViewModels {
        HomeViewModel.HomeViewModelFactory(
            MovieApiService.getInstance().create(MovieRepositoryApiInterface::class.java)
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initViewPager()
        initRecycler()
    }

    override fun onStart() {
        super.onStart()

        viewModel.popularPage = 1
        viewModel.upcomingPage = 1
        viewModel.ratedPage = 1

        viewModel.popularList.observe(this) { list ->
            var positionList = mPopularAdapterApi.list.size
            mPopularAdapterApi.list.addAll(list)
            mPopularAdapterApi.notifyItemInserted(positionList)
        }

        viewModel.slideList.observe(this){ list ->
            mPagerSliderAdapter.setListSliders(list)
            mPagerSliderAdapter.notifyDataSetChanged()
        }

        viewModel.topratedList.observe(this) {  list ->
            if(list.size > 0){
                var positionList = mToprateAdapterApi.list.size
                mToprateAdapterApi.list.addAll(list)
                mToprateAdapterApi.notifyItemInserted(positionList)
            }else{
                Toast.makeText(requireContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.upcomingList.observe(this){ list ->
            var positionList = mUpcomingAdapterApi.list.size
            mUpcomingAdapterApi.list.addAll(list    )
            mUpcomingAdapterApi.notifyItemInserted(positionList)

        }

        binding.popularRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!recyclerView.canScrollHorizontally(1)){
                    binding.loadArrowRecyclerPopular.visibility = View.VISIBLE
                    viewModel.updatePopularList(viewModel.popularPage)
                }

                if(recyclerView.canScrollHorizontally(1)){
                    binding.loadArrowRecyclerPopular.visibility = View.GONE
                }
            }
        })

        binding.topraredRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(!recyclerView.canScrollHorizontally(1)){
                    binding.loadArrowRecyclerToprated.visibility = View.VISIBLE
                    viewModel.updateTopratedList(viewModel.ratedPage)
                }

                if(recyclerView.canScrollHorizontally(1)){
                    binding.loadArrowRecyclerToprated.visibility = View.GONE
                }
            }
        })

        binding.upcomingRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollHorizontally(1)){
                    binding.loadArrowRecyclerUpcoming.visibility = View.VISIBLE
                    viewModel.updateUpcomingList(viewModel.upcomingPage)
                }

                if(recyclerView.canScrollHorizontally(1)){
                    binding.loadArrowRecyclerUpcoming.visibility = View.GONE
                }
            }
        })


        timer.scheduleAtFixedRate(SliderTimer(this), 4000, 6000)

    }

    private fun initRecycler() {


        mToprateAdapterApi = MoviesRecyclerAdapterApi{
            val bottomsheet = BottomSheetDetail()
            val bundle = Bundle().apply {
                putString("movieId", it.id)
                putString("title", it.title)
                putString("poster", it.poster)
                putString("backdrop", it.backdrop)
                putString("overview", it.overview)
                putString("realese", it.realese)
                putString("voteAverage", it.voteAverage)
                putBoolean("mylistCall", false)
            }

            bottomsheet.arguments = bundle
            bottomsheet.show(childFragmentManager, "bottomsheetToprated")
        }

        mPopularAdapterApi = MoviesRecyclerAdapterApi{
            val bottomsheet = BottomSheetDetail()
            val bundle = Bundle().apply {
                putString("movieId", it.id)
                putString("title", it.title)
                putString("poster", it.poster)
                putString("backdrop", it.backdrop)
                putString("overview", it.overview)
                putString("realese", it.realese)
                putString("voteAverage", it.voteAverage)
                putBoolean("mylistCall", false)
            }
            bottomsheet.arguments = bundle
            bottomsheet.show(childFragmentManager, "bottomsheetPopular")
        }

        mUpcomingAdapterApi = MoviesRecyclerAdapterApi{
            val bottomsheet = BottomSheetDetail()
            val bundle = Bundle().apply {
                putString("movieId", it.id)
                putString("title", it.title)
                putString("poster", it.poster)
                putString("backdrop", it.backdrop)
                putString("overview", it.overview)
                putString("realese", it.realese)
                putString("voteAverage", it.voteAverage)
                putBoolean("mylistCall", false)
            }

            bottomsheet.arguments = bundle
            bottomsheet.show(childFragmentManager, "bottomsheetToprated")
        }

        binding.upcomingRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mUpcomingAdapterApi
        }

        binding.topraredRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mToprateAdapterApi
        }

        binding.popularRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mPopularAdapterApi
        }
    }

    private fun initViewPager(){
        mPagerSliderAdapter = MoviesSliderAdapter(requireContext()){
            val bottomsheet = BottomSheetDetail()
            val bundle = Bundle().apply {
                putString("movieId", it.id)
                putString("title", it.title)
                putString("poster", it.poster)
                putString("backdrop", it.backdrop)
                putString("overview", it.overview)
                putString("realese", it.realese)
                putString("voteAverage", it.voteAverage)
            }

            bottomsheet.arguments = bundle
            bottomsheet.show(childFragmentManager, "bottomsheetViewPager")
        }
        binding.viewPagerSlider.adapter = mPagerSliderAdapter
    }

    inner class SliderTimer(private val fragment : Fragment) : TimerTask(){

        override fun run() {
                fragment.requireActivity().runOnUiThread(object : Runnable{
                    override fun run() {
                        if(mPagerSliderAdapter.getListSliders().size > 0){
                            if(binding.viewPagerSlider.currentItem < viewModel.topratedList.value!!.size - 1){
                                binding.viewPagerSlider.currentItem = binding.viewPagerSlider.currentItem + 1
                            }else{
                                binding.viewPagerSlider.currentItem = 0
                            }
                        }

                    }
                })
            }
    }


    override fun onPause() {
        super.onPause()
        timer.cancel()
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        _binding = null
    }
}