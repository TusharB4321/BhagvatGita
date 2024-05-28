package com.example.bhagvatgita.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagvatgita.databinding.ItemViewVersesBinding

class VersesAdapter(val verseItemClicked: (String, Int) -> Unit, val onClick: Boolean) :RecyclerView.Adapter<VersesAdapter.VersesViewHolder>() {
    inner class VersesViewHolder(val binding: ItemViewVersesBinding): RecyclerView.ViewHolder(binding.root)
    private val differUtil=object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
           return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersesAdapter.VersesViewHolder {
        return VersesViewHolder(
            ItemViewVersesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VersesAdapter.VersesViewHolder, position: Int) {
       val chapterList=differ.currentList[position]

        holder.binding.apply {
            tvVerseNumber.text="Verse ${position+1}"
            tvVerseDescription.text= chapterList
        }

        if (!onClick){
            holder.binding.ivNext.visibility= View.GONE
        }
        holder.binding.ll.setOnClickListener {
            if (onClick){
                verseItemClicked(chapterList,position+1)
            }

        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}