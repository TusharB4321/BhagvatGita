package com.example.bhagvatgita.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagvatgita.databinding.ItemViewVersesBinding
import com.example.bhagvatgita.datasource.room.SavedVerses

class SavedVersesAdapter(val verseItemClicked: (SavedVerses) -> Unit) :RecyclerView.Adapter<SavedVersesAdapter.VersesViewHolder>() {
    inner class VersesViewHolder(val binding: ItemViewVersesBinding): RecyclerView.ViewHolder(binding.root)

    private val differUtil=object : DiffUtil.ItemCallback<SavedVerses>(){
        override fun areItemsTheSame(oldItem: SavedVerses, newItem: SavedVerses): Boolean {
            return oldItem==newItem
        }
        override fun areContentsTheSame(oldItem: SavedVerses, newItem: SavedVerses): Boolean {
           return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedVersesAdapter.VersesViewHolder {
        return VersesViewHolder(
            ItemViewVersesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SavedVersesAdapter.VersesViewHolder, position: Int) {
       val chapterList=differ.currentList[position]

        holder.binding.apply {
            tvVerseNumber.text="Verse ${chapterList.chapter_number}.${chapterList.verse_number}"
            tvVerseDescription.text= chapterList.translations[0].description
        }

        holder.binding.ll.setOnClickListener {
            verseItemClicked(chapterList)
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}