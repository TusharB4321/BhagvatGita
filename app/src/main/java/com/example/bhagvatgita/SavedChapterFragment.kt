package com.example.bhagvatgita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bhagvatgita.adapters.ChapterAdapter
import com.example.bhagvatgita.databinding.FragmentSavedChapterBinding
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.utils.Common
import com.example.bhagvatgita.viewmodel.MainViewmodel

class SavedChapterFragment : Fragment() {
    private lateinit var binding:FragmentSavedChapterBinding
    private val viewmodel:MainViewmodel by viewModels()
    private lateinit var chapterAdapter: ChapterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSavedChapterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Common.changeStatusBarColor(requireActivity(),R.color.white)

        getSavedChapter()
    }

    private fun getSavedChapter() {
        viewmodel.getSavedChapter().observe(viewLifecycleOwner){
            val chapterList= arrayListOf<ChaptersModelItem>()

            for (i in it){
                val chapterItem=ChaptersModelItem(
                    i.chapter_number,
                    i.chapter_summary,
                    i.chapter_summary_hindi,
                    i.id,
                    i.name,
                    i.name_meaning,
                    i.name_translated,
                    i.name_transliterated,
                    i.slug,
                    i.verses_count
                )
                chapterList.add(chapterItem)
            }

            if (chapterList.isEmpty()){
                binding.shimmer.visibility=View.GONE
                binding.rvChapter.visibility=View.GONE
                binding.tvShowingMessage.visibility=View.GONE
            }

            chapterAdapter= ChapterAdapter(
                ::onClickedChapterItem,
                null,
                false,
                ::onFavouriteFilledClicked,
                viewmodel
            )
            binding.rvChapter.adapter=chapterAdapter
            chapterAdapter.differ.submitList(chapterList)

            binding.shimmer.visibility=View.GONE
            binding.rvChapter.visibility=View.VISIBLE
        }
    }

    private fun onClickedChapterItem(chaptersModelItem: ChaptersModelItem){
        val bundle=Bundle()
        bundle.putInt("chapterNumber",chaptersModelItem.chapter_number)
        bundle.putBoolean("showRoomData",true)

        findNavController().navigate(R.id.action_savedChapterFragment_to_versesFragment,bundle)
    }

    private fun onFavouriteFilledClicked(chaptersModelItem: ChaptersModelItem){

    }
}