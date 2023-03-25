package com.example.pklparentinghub.ui.main.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.data.model.userContent.Article
import com.example.pklparentinghub.databinding.ItemArticleProfileBinding

class ProfileArtichleAdapter: RecyclerView.Adapter<ProfileArtichleAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(private val binding: ItemArticleProfileBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun setData(item: Article){
            binding.apply {
                val artichlePicture = item.author.profilePicture
                val picture = itemProfilePicture
                Glide.with(picture)
                    .load(artichlePicture)
                    .into(picture)
                val artichleBanner = item.thumbnail
                val cover = itemCover
                Glide.with(cover)
                    .load(artichleBanner)
                    .into(cover)
                itemFullName.text = item.author.fullName
                itemLike.text = item.likesCount.toString()
                itemTitle.text = item.title
                itemTime.text = item.createdAt

                Log.e(ContentValues.TAG, "setupObserver: ${item.thumbnail}", )
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

    var items : List<Article>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder(
        ItemArticleProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}