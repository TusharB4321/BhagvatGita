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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.bhagvatgita.R
import com.example.bhagvatgita.databinding.FragmentVerseDetailBinding
import com.example.bhagvatgita.datasource.model.Commentary
import com.example.bhagvatgita.datasource.model.Translation
import com.example.bhagvatgita.datasource.model.VersesItem
import com.example.bhagvatgita.datasource.room.SavedVerses
import com.example.bhagvatgita.network.NetworkManager
import com.example.bhagvatgita.utils.Common
import com.example.bhagvatgita.utils.Common.changeStatusBarColor
import com.example.bhagvatgita.viewmodel.MainViewmodel
import kotlinx.coroutines.launch

class VerseDetailFragment : Fragment() {

    private lateinit var binding:FragmentVerseDetailBinding
    private val viewmodel:MainViewmodel by viewModels()
    private var chapterNum=0
    private var verseNum=0
    private var verse= MutableLiveData<VersesItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentVerseDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeStatusBarColor(requireActivity(),R.color.white)
        getAndSetChapterVerseNumber()
        checkInternetConnectivity()
        readMoreFunctionality()
        favouriteFunctionality()
    }

    private fun favouriteFunctionality() {
        binding.ivFavourite.setOnClickListener {
            binding.ivFavourite.visibility=View.GONE
            binding.ivFavouriteFilled.visibility=View.VISIBLE

            saveVerseDetails()
        }
        binding.ivFavouriteFilled.setOnClickListener {
            binding.ivFavourite.visibility=View.VISIBLE
            binding.ivFavouriteFilled.visibility=View.GONE
        }
    }

    private fun saveVerseDetails() {
        verse.observe(viewLifecycleOwner){

            val englishTranslationList= arrayListOf<Translation>()

            for (i in it.translations){
                if (i.language=="english"){
                    englishTranslationList.add(i)
                }
            }
            val englishCommentaryList= arrayListOf<Commentary>()

            for (i in it.commentaries){
                if (i.language=="hindi"){
                    englishCommentaryList.add(i)
                }
            }
            val savedVerses=SavedVerses(
                it.chapter_number,
                commentaries = englishCommentaryList,
                it.id,
                it.slug,
                it.text,
                englishTranslationList,
                it.transliteration,
                it.verse_number,
                it.word_meanings
            )

            lifecycleScope.launch {
                viewmodel.insertVerses(savedVerses)
            }

        }
    }

    private fun getVerseDetails() {

        lifecycleScope.launch {
            viewmodel.getPerticularVerse(chapterNum,verseNum).collect{verseDetails->
                binding.tvVerseText.text=verseDetails.text
                binding.tvVerseTranslationEnglish.text=verseDetails.transliteration
                binding.tvWordOfEng.text=verseDetails.word_meanings

                val englishTranslationList= arrayListOf<Translation>()

                for (i in verseDetails.translations){
                    if (i.language=="english"){
                        englishTranslationList.add(i)
                    }
                }

                val englishTranslationListSize= englishTranslationList.size

                if (englishTranslationList.isNotEmpty()){
                    binding.tvAuthorName.text=englishTranslationList[0].author_name
                    binding.tvText.text=englishTranslationList[0].description

                    if (englishTranslationListSize==1) binding.fabTranslationRight.visibility=View.GONE

                    var i=0
                    binding.fabTranslationRight.setOnClickListener {
                        if (i<englishTranslationListSize-1){
                            i++
                            binding.tvAuthorName.text=englishTranslationList[i].author_name
                            binding.tvText.text=englishTranslationList[i].description
                            binding.fabTranslationLeft.visibility=View.VISIBLE

                            if (i==englishTranslationListSize-1) binding.fabTranslationRight.visibility=View.GONE
                        }
                    }

                    binding.fabTranslationLeft.setOnClickListener {
                        if (i>0){
                            i--
                            binding.tvAuthorName.text=englishTranslationList[i].author_name
                            binding.tvText.text=englishTranslationList[i].description
                            binding.fabTranslationRight.visibility=View.VISIBLE

                            if (i==0) binding.fabTranslationLeft.visibility=View.GONE
                        }
                    }

                    }
                // For Commentary
                 val englishCommentaryList= arrayListOf<Commentary>()

                for (i in verseDetails.commentaries){
                    if (i.language=="hindi"){
                        englishCommentaryList.add(i)
                    }
                }

                val englishCommentarySize= englishCommentaryList.size

                if (englishCommentaryList.isNotEmpty()){
                    binding.tvAuthorNameCommentary.text=englishCommentaryList[0].author_name
                    binding.tvTextCommentary.text=englishCommentaryList[0].description

                    if (englishCommentarySize==1) binding.fabTranslationRightCommentary.visibility=View.GONE

                    var i=0
                    binding.fabTranslationRightCommentary.setOnClickListener {
                        if (i<englishCommentarySize-1){
                            i++
                            binding.tvAuthorNameCommentary.text=englishCommentaryList[i].author_name
                            binding.tvTextCommentary.text=englishCommentaryList[i].description
                            binding.fabTranslationLeftCommentary.visibility=View.VISIBLE

                            if (i==englishCommentarySize-1) binding.fabTranslationRightCommentary.visibility=View.GONE
                        }
                    }

                    binding.fabTranslationLeftCommentary.setOnClickListener {
                        if (i>0){
                            i--
                            binding.tvAuthorNameCommentary.text=englishCommentaryList[i].author_name
                            binding.tvTextCommentary.text=englishCommentaryList[i].description
                            binding.fabTranslationRightCommentary.visibility=View.VISIBLE

                            if (i==0) binding.fabTranslationLeftCommentary.visibility=View.GONE
                        }
                    }

                }
            }
            binding.progressBar.visibility=View.GONE
            visibleView()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getAndSetChapterVerseNumber() {
        val bundle=arguments
        chapterNum=bundle!!.getInt("chapterNum")
        verseNum=bundle.getInt("verseNum")
        binding.tvVerseNumber.text="|| $chapterNum.$verseNum ||"
    }

    private fun readMoreFunctionality() {
        var isRead=false

        binding.tvSeeMore.setOnClickListener {
            if (!isRead){
                binding.tvTextCommentary.maxLines=50
                isRead=true
            }else{
                binding.tvTextCommentary.maxLines=4
                isRead=false
            }
        }
    }

    private fun checkInternetConnectivity() {
        val networkManager= NetworkManager(requireContext())

        networkManager.observe(viewLifecycleOwner){

            if (it==true){
                binding.tvShowingMessage.visibility=View.GONE
                binding.progressBar.visibility=View.VISIBLE
                getVerseDetails()
            }else{
                binding.tvShowingMessage.visibility=View.VISIBLE
                binding.iv.visibility=View.VISIBLE
                binding.progressBar.visibility=View.GONE
                visibleGone()
            }
        }
    }

    private fun visibleView(){
        binding.llVisibility.visibility=View.VISIBLE
        binding.backgroundImage.visibility=View.VISIBLE
        binding.llBottom.visibility=View.VISIBLE
        binding.ivFavourite.visibility=View.VISIBLE

    }
    private fun visibleGone(){
        binding.llVisibility.visibility=View.GONE
        binding.backgroundImage.visibility=View.GONE
        binding.llBottom.visibility=View.GONE
        binding.ivFavourite.visibility=View.GONE

    }

}