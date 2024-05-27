package com.example.bhagvatgita.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagvatgita.databinding.ItemViewChaptersBinding
import com.example.bhagvatgita.datasource.model.ChaptersModelItem

class ChapterAdapter(
    val onItemClicked: (ChaptersModelItem) -> Unit,
    val onFavItemClicked: ((ChaptersModelItem) -> Unit)?
) :RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(val binding: ItemViewChaptersBinding): RecyclerView.ViewHolder(binding.root)


    val differUtil=object : DiffUtil.ItemCallback<ChaptersModelItem>(){
        override fun areItemsTheSame(
            oldItem: ChaptersModelItem,
            newItem: ChaptersModelItem
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ChaptersModelItem,
            newItem: ChaptersModelItem
        ): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterAdapter.ChapterViewHolder {

        return ChapterViewHolder(
            ItemViewChaptersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChapterAdapter.ChapterViewHolder, position: Int) {
       val chapterList=differ.currentList[position]
        holder.binding.apply {
            tvChapterNumber.text="Chapter ${chapterList.chapter_number}"
            tvChapterName.text=chapterList.name_translated
            tvChapterDescription.text=chapterList.chapter_summary
            tvVersesCount.text=chapterList.verses_count.toString()
        }

        holder.binding.ll.setOnClickListener {
            onItemClicked(chapterList)
        }

        holder.binding.apply {
            ivFavourite.setOnClickListener {
                onFavItemClicked?.let { it1 -> it1(chapterList) }
            }
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}