package com.example.bhagvatgita.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bhagvatgita.R
import com.example.bhagvatgita.adapters.ChapterAdapter
import com.example.bhagvatgita.databinding.FragmentHomeBinding
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.room.SaveChapters
import com.example.bhagvatgita.network.NetworkManager
import com.example.bhagvatgita.utils.Common.changeStatusBarColor
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun onFavouriteClicked(chapterItem:ChaptersModelItem){
        lifecycleScope.launch {
            viewmodel.getAllVerses(chapterItem.chapter_number).collect{VerseList->
                val list= arrayListOf<String>()
                for (currentList in VerseList){
                    for (verseList in currentList.translations){
                        if (verseList.language== "english"){
                            list.add(verseList.description)
                            break
                        }
                    }
                }
                val saveChapter=SaveChapters(
                    chapter_number = chapterItem.chapter_number,
                    chapter_summary = chapterItem.chapter_summary,
                    chapter_summary_hindi = chapterItem.chapter_summary_hindi,
                    id=chapterItem.id,
                    name = chapterItem.name,
                    name_meaning = chapterItem.name_meaning,
                    name_translated = chapterItem.name_translated,
                    name_transliterated = chapterItem.name_transliterated,
                    slug = chapterItem.slug,
                    verses_count = chapterItem.verses_count,
                    verses = list
                )
                viewmodel.insertChapters(saveChapter)
            }
        }
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
              chapterAdapter= ChapterAdapter(::onItemClicked, ::onFavouriteClicked, true)
              binding.rvChapter.adapter=chapterAdapter
              chapterAdapter.differ.submitList(chapterList)
              binding.shimmer.visibility=View.GONE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternetConnectivity()
        changeStatusBarColor(requireActivity(),R.color.white)
        binding.ivSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
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