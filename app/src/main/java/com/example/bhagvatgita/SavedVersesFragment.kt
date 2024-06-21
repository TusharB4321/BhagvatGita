package com.example.bhagvatgita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bhagvatgita.adapters.SavedVersesAdapter
import com.example.bhagvatgita.adapters.VersesAdapter
import com.example.bhagvatgita.databinding.FragmentSavedVersesBinding
import com.example.bhagvatgita.datasource.room.SavedVerses
import com.example.bhagvatgita.viewmodel.MainViewmodel

class SavedVersesFragment : Fragment() {
    private lateinit var binding:FragmentSavedVersesBinding
    private val viewmodel:MainViewmodel by viewModels()
    private lateinit var savedVersesAdapter: SavedVersesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSavedVersesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getVerseFromRoom()
    }

    private fun getVerseFromRoom() {
        viewmodel.getAllEnglishVerse().observe(viewLifecycleOwner){
            savedVersesAdapter= SavedVersesAdapter(::onItemClicked)
            binding.rvVerses.adapter=savedVersesAdapter
            binding.rvVerses.layoutManager= LinearLayoutManager(requireContext())
            savedVersesAdapter.differ.submitList(it)
            binding.shimmer.visibility=View.GONE
        }
    }
    private fun onItemClicked(verse: SavedVerses) {

        val bundle=Bundle()
        bundle.putBoolean("showRoomData",true)
        bundle.putInt("chapterNum",verse.chapter_number)
        bundle.putInt("verseNum",verse.verse_number)
        findNavController().navigate(R.id.action_savedVersesFragment_to_verseDetailFragment,bundle)
    }

}