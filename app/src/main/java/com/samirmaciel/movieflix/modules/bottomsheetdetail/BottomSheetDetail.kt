package com.samirmaciel.movieflix.modules.bottomsheetdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.state.State
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentBottomsheetdetailBinding

class BottomSheetDetail : BottomSheetDialogFragment() {

    private var _binding : FragmentBottomsheetdetailBinding? = null
    private val binding : FragmentBottomsheetdetailBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottomsheetdetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBottomsheetdetailBinding.bind(view)

    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog

        if(dialog != null){
            val bottomsheet = dialog.findViewById<ConstraintLayout>(R.id.bottomsheetdetail)
            bottomsheet.setBackgroundColor(Color.TRANSPARENT)
        }
    }





    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}