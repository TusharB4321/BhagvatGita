package com.example.bhagvatgita.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhagvatgita.databinding.ItemViewChaptersBinding
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.viewmodel.MainViewmodel

class ChapterAdapter(
    val onItemClicked: (ChaptersModelItem) -> Unit,
    val onFavItemClicked: ((ChaptersModelItem) -> Unit)?,
    val isFavourite: Boolean,
    val onFavouriteFilledClicked: (ChaptersModelItem) -> Unit,
    val viewmodel: MainViewmodel
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

        val keys=viewmodel.getAllSaveChapterKeySp()

        if (!isFavourite) {
            holder.binding.ivFavourite.visibility = View.GONE
            holder.binding.ivFavouriteFilled.visibility = View.GONE
        }else{
            if (keys.contains(chapterList.chapter_number.toString())){
                holder.binding.ivFavourite.visibility = View.GONE
                holder.binding.ivFavouriteFilled.visibility = View.VISIBLE
            }else{
                holder.binding.ivFavourite.visibility = View.VISIBLE
                holder.binding.ivFavouriteFilled.visibility = View.GONE
            }
        }
        holder.binding.apply {
            ivFavourite.setOnClickListener {
                ivFavouriteFilled.visibility=View.VISIBLE
                ivFavourite.visibility=View.GONE
                onFavItemClicked?.let { it1 -> it1(chapterList) }
            }

            ivFavouriteFilled.setOnClickListener {
                ivFavourite.visibility=View.VISIBLE
                ivFavouriteFilled.visibility=View.GONE
                onFavouriteFilledClicked(chapterList)
            }
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}