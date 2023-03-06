package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pklparentinghub.data.model.ImageData
import com.example.pklparentinghub.databinding.ItemHomeArticleSliderBinding

class ArticleHomeArticleSliderAdapter(private val items: List<ImageData>) :
    RecyclerView.Adapter<ArticleHomeArticleSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: ItemHomeArticleSliderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bind(data: ImageData) {
            with(binding) {
                Glide.with(itemView)
                    .load(data.image)
                    .transform(CenterCrop(), RoundedCorners(6))
                    .into(ivArticle)
                tvTitle.text = data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        ItemHomeArticleSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }
}