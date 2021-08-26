package com.samirmaciel.movieflix.modules.mylist_page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentMylistBinding
import com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watched_page.WatchedViewModel
import com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watchlater_page.WatchLaterViewModel
import com.samirmaciel.movieflix.shared.adapter.MyListViewPagerAdapter
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchLaterRepositoryLocal
import com.samirmaciel.movieflix.shared.repository.local.MovieWatchedRepositoryLocal

class FragmentMylistPage : Fragment(R.layout.fragment_mylist){

    private var _binding : FragmentMylistBinding? = null
    private val binding : FragmentMylistBinding get() = _binding!!

    private val viewModelWatched : WatchedViewModel by activityViewModels {

        WatchedViewModel.WatchedViewModelFactory(
            MovieWatchedRepositoryLocal(AppDatabase.getDatabase(requireContext()).MovieWatchedDao())
        )
    }

    private val viewModelWatchLater : WatchLaterViewModel by activityViewModels {

        WatchLaterViewModel.WatchLaterViewModelFactory(
            MovieWatchLaterRepositoryLocal(
                AppDatabase.getDatabase(requireContext()).MovieWatchLaterDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMylistBinding.bind(view)

        val viewPagerAdapter = MyListViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPagerForTab.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerForTab){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Assitir mais tarde"
                }

                1-> {
                    tab.text = "Visto"
                }
            }

        }.attach()

    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}