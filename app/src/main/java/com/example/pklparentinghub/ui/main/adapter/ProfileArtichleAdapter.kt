package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.data.model.userContent.Data
import com.example.pklparentinghub.databinding.ItemArticleProfileBinding

class ProfileArtichleAdapter: RecyclerView.Adapter<ProfileArtichleAdapter.ArtichleViewHolder>() {
    inner class ArtichleViewHolder(private val binding: ItemArticleProfileBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun setData(item: Data){
            binding.apply {
                val artichlePicture = item.profilePicture
                val picture = itemProfilePicture
                Glide.with(picture)
                    .load(artichlePicture)
                    .into(picture)
                val artichleBanner = item.profileCover
                val cover = itemCover
                Glide.with(cover)
                    .load(artichleBanner)
                    .into(cover)
                itemFullName.text = item.username
                itemLike.text = item.likedArticle.toString()
//                itemTitle.text = item.
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ArtichleViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

    var items : List<Data>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArtichleViewHolder(
        ItemArticleProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}