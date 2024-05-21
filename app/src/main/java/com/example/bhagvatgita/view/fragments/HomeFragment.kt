package com.example.bhagvatgita.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bhagvatgita.R
import com.example.bhagvatgita.adapters.ChapterAdapter
import com.example.bhagvatgita.databinding.FragmentHomeBinding
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.network.NetworkManager
import com.example.bhagvatgita.viewmodel.MainViewmodel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var chapterAdapter: ChapterAdapter
    private val viewmodel:MainViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun checkInternetConnectivity() {
        val networkManager=NetworkManager(requireContext())

        networkManager.observe(viewLifecycleOwner){

            if (it==true){
                binding.shimmer.visibility=View.VISIBLE
                binding.rvChapter.visibility=View.VISIBLE
                binding.tvShowingMessage.visibility=View.GONE
                getAllChapters()
            }else{
               binding.shimmer.visibility=View.GONE
               binding.rvChapter.visibility=View.GONE
               binding.tvShowingMessage.visibility=View.VISIBLE
            }
        }
    }

    private fun getAllChapters() {
        lifecycleScope.launch {
            viewmodel.getAllChapters().collect{chapterList->
              chapterAdapter= ChapterAdapter(::onItemClicked)
              binding.rvChapter.adapter=chapterAdapter
              chapterAdapter.differ.submitList(chapterList)
              binding.shimmer.visibility=View.GONE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnectivity()
        changeStatusBarColor()
    }

    private fun changeStatusBarColor() {
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun onItemClicked(chaptersModelItem: ChaptersModelItem){
        val bundle=Bundle()
        bundle.putInt("chapterNumber",chaptersModelItem.chapter_number)
        bundle.putInt("verseCount",chaptersModelItem.verses_count)
        bundle.putString("chapterTitle",chaptersModelItem.name_translated)
        bundle.putString("chapterDesc",chaptersModelItem.chapter_summary)
        findNavController().navigate(R.id.action_homeFragment_to_versesFragment,bundle)
    }
}