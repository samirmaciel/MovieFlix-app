package com.samirmaciel.movieflix.modules.mylist_page.fragment_pages.watchlater_page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentWatchedBinding

class FragmentWatchLater : Fragment(R.layout.fragment_watch_later) {

    private var _binding : FragmentWatchedBinding? = null
    private val binding : FragmentWatchedBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWatchedBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}