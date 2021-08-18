package com.samirmaciel.movieflix.modules.search_page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentSearchBinding

class FragmentSearchPage : Fragment(R.layout.fragment_search){

    private var _binding : FragmentSearchBinding? = null
    private val binding : FragmentSearchBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}