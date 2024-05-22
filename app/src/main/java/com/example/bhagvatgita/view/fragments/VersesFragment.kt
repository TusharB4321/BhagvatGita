package com.example.bhagvatgita.view.fragments

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bhagvatgita.R
import com.example.bhagvatgita.adapters.VersesAdapter
import com.example.bhagvatgita.databinding.FragmentVersesBinding
import com.example.bhagvatgita.datasource.model.VersesItem
import com.example.bhagvatgita.network.NetworkManager
import com.example.bhagvatgita.viewmodel.MainViewmodel
import kotlinx.coroutines.launch

class VersesFragment : Fragment() {
    private lateinit var binding:FragmentVersesBinding
    private lateinit var versesAdapter: VersesAdapter
    private val viewmodel:MainViewmodel by viewModels()
    private var chapterNum=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentVersesBinding.inflate(layoutInflater)
        readMoreFunctionality()
        getAndSetVerse()
        checkInternetConnectivity()
        changeStatusBarColor()
        return binding.root
    }
    private fun getAllVerses() {
        lifecycleScope.launch {
            viewmodel.getAllVerses(chapterNum).collect{VerseList->
                versesAdapter= VersesAdapter(::onItemClicked)
                binding.rvVerses.adapter=versesAdapter
                binding.rvVerses.layoutManager=LinearLayoutManager(requireContext())
                val list= arrayListOf<String>()
                for (currentList in VerseList){
                    for (verseList in currentList.translations){
                        if (verseList.language== "english"){
                            list.add(verseList.description)
                            break
                        }
                    }
                }
                versesAdapter.differ.submitList(list)
                binding.shimmer.visibility=View.GONE
            }
        }
   }
    private fun readMoreFunctionality() {
        var isRead=false

        binding.tvReadMore.setOnClickListener {
            if (!isRead){
                binding.tvDescription.maxLines=50
                isRead=true
            }else{
                binding.tvDescription.maxLines=4
                isRead=false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getAndSetVerse() {
        val bundle=arguments
        chapterNum=bundle?.getInt("chapterNumber")!!
        binding.tvChapterNo.text="Chapter ${bundle.getInt("chapterNumber")}"
        binding.tvVerseName.text= bundle.getString("chapterTitle")
        binding.tvDescription.text= bundle.getString("chapterDesc")
        binding.tvNumberOfVerses.text= bundle.getInt("verseCount").toString()+" Verses"
    }

    private fun onItemClicked(verse:String,verseNumber: Int){

        val bundle=Bundle()
        bundle.putInt("chapterNum",chapterNum)
        bundle.putInt("verseNum",verseNumber)

        findNavController().navigate(R.id.action_versesFragment_to_verseDetailFragment,bundle)
    }

    private fun changeStatusBarColor() {
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun checkInternetConnectivity() {
        val networkManager= NetworkManager(requireContext())

        networkManager.observe(viewLifecycleOwner){

            if (it==true){
                binding.shimmer.visibility=View.VISIBLE
                binding.rvVerses.visibility=View.VISIBLE
                binding.tvShowingMessage.visibility=View.GONE
                getAllVerses()
            }else{
                binding.shimmer.visibility=View.GONE
                binding.rvVerses.visibility=View.GONE
                binding.tvShowingMessage.visibility=View.VISIBLE
            }
        }
    }

}