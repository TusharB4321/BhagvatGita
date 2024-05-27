package com.example.bhagvatgita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhagvatgita.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llSavedChapter.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_savedChapterFragment)
        }
        binding.llSavedVerse.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_savedVersesFragment)
        }
    }

}