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
import com.example.pklparentinghub.databinding.ItemHomeArticleSliderBinding

class ArticleHomeSliderAdapter() : RecyclerView.Adapter<ArticleHomeSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(private val binding: ItemHomeArticleSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: Article) {
            val title = data.title
            val imageBanner = data.thumbnail

            binding.apply {
                Glide.with(ivArticle.context)
                    .load(imageBanner)
                    .transform(CenterCrop(), RoundedCorners(10))
                    .into(ivArticle)
                tvTitle.text = title
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
    var items: List<Article>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        ItemHomeArticleSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }
}