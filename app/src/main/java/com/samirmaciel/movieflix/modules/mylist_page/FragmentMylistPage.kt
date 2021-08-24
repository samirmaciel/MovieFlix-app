package com.samirmaciel.movieflix.modules.mylist_page

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.samirmaciel.movieflix.R
import com.samirmaciel.movieflix.databinding.FragmentMylistBinding
import com.samirmaciel.movieflix.shared.adapter.MoviesRecyclerAdapterMylist
import com.samirmaciel.movieflix.shared.adapter.MyListViewPagerAdapter
import com.samirmaciel.movieflix.shared.localdata.AppDatabase
import com.samirmaciel.movieflix.shared.repository.local.MovieRepositoryLocal

class FragmentMylistPage : Fragment(R.layout.fragment_mylist){

    private var _binding : FragmentMylistBinding? = null
    private val binding : FragmentMylistBinding get() = _binding!!

    private val viewModel : MylistViewModel by activityViewModels {
        MylistViewModel.MylistViewModelFactory(
            MovieRepositoryLocal(
                AppDatabase.getDatabase(
                    requireContext()
                ).MovieDao()
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

//    private fun initRecyclerView(){
//        adapterRecyclerView = MoviesRecyclerAdapterMylist({ position, movie ->
//            val alert = AlertDialog.Builder(requireContext()).apply {
//                setTitle("Tem certeza que deseja remover este filme?")
//                setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
//                    viewModel.deleteById(movie.movieId)
//                    adapterRecyclerView.list.remove(movie)
//                    adapterRecyclerView.notifyItemRemoved(position)
//                })
//                setNegativeButton("Não", null)
//            }.create().show()
//        }, {
//
//        })
//
//        binding.mylistRecyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = adapterRecyclerView
//        }
//    }

    override fun onStart() {
        super.onStart()


    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}