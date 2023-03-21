package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pklparentinghub.data.model.articleData.Article
import com.example.pklparentinghub.databinding.ItemHomeArticleBinding
import com.example.pklparentinghub.databinding.ItemHomeArticleSliderBinding

class ArticleHomeAdapter : RecyclerView.Adapter<ArticleHomeAdapter.ViewHolder>() {

    class ViewHolder (private val binding: ItemHomeArticleBinding) : RecyclerView.ViewHolder(binding.root) {
            fun setData(item: Article) {
                binding.apply {
                    itemTitle.text = item.title
                    itemTime.text = item.createdAt

                    val valuesThumbnail = item.thumbnail
                    val thumbnail = itemCover
                    Glide.with(thumbnail)
                        .load(valuesThumbnail)
                        .into(thumbnail)
                }
            }
        }

        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ) = oldItem == newItem
        }

        private val differ = AsyncListDiffer(this, differCallback)

        var items : List<Article>
            get() = differ.currentList
            set(value) = differ.submitList(value)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            ItemHomeArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setData(items[position])
            holder.setIsRecyclable(true)
        }

        override fun getItemCount(): Int = items.size
}