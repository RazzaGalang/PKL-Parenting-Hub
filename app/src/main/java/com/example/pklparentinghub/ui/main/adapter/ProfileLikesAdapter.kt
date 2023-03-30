package com.example.pklparentinghub.ui.main.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.data.model.userContent.LikedArticle
import com.example.pklparentinghub.databinding.ItemArticleProfileBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class ProfileLikesAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<ProfileLikesAdapter.LikesViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: LikedArticle)
    }

    inner class LikesViewHolder(private val binding: ItemArticleProfileBinding):
            RecyclerView.ViewHolder(binding.root){
                fun setData(item: LikedArticle) {
                    binding.apply {
                        binding.itemIconLike.setOnClickListener { listener.onItemClick(item) }

                        val date = item.createdAt.substring(0, 9)
                        val df : DateFormat = SimpleDateFormat ("yyyy-MM-dd")
                        val dateFormat: DateFormat = SimpleDateFormat ("dd MMMM yyyy")
                        val newDate: String = dateFormat.format(df.parse(date))
                        val articlePicture = item.author.profilePicture
                        val picture = itemProfilePicture
                        Glide.with(picture)
                            .load(articlePicture)
                            .into(picture)
                        Log.e(ContentValues.TAG, "setData: $articlePicture", )
                        val articleBanner = item.thumbnail
                        val cover = itemCover
                        Glide.with(cover)
                            .load(articleBanner)
                            .into(cover)
                        Log.e(ContentValues.TAG, "setData: $articleBanner", )
                        itemFullName.text = item.author.fullName
                        itemLike.text = item.likesCount.toString()
                        itemTitle.text = item.title
                        itemTime.text = newDate
                    }
                }
            }

    private val differCallback = object : DiffUtil.ItemCallback<LikedArticle>(){
        override fun areItemsTheSame(oldItem: LikedArticle, newItem: LikedArticle): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LikedArticle, newItem: LikedArticle): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ProfileLikesAdapter.LikesViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

    var items : List<LikedArticle>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LikesViewHolder(
        ItemArticleProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}