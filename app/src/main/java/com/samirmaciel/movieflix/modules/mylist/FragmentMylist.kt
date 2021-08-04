package com.samirmaciel.movieflix.modules.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentMylistBinding

class FragmentMylist : Fragment(R.layout.fragment_mylist) {

    private var _binding : FragmentMylistBinding? = null
    private val binding : FragmentMylistBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMylistBinding.bind(view)



    }




    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}