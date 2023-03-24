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
import java.text.DateFormat
import java.text.SimpleDateFormat

class ArticleHomeAdapter : RecyclerView.Adapter<ArticleHomeAdapter.ViewHolder>() {

    class ViewHolder (private val binding: ItemHomeArticleBinding) : RecyclerView.ViewHolder(binding.root) {
            fun setData(item: Article) {
                binding.apply {
                    val date = item.createdAt.substring(0, 9)
                    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val dateFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
                    val newDate: String = dateFormat.format(df.parse(date))
                    val valuesThumbnail = item.thumbnail
                    val thumbnail = itemCover
                    Glide.with(thumbnail)
                        .load(valuesThumbnail)
                        .into(thumbnail)
                    val valuesProfile = item.thumbnail
                    val profile = itemProfilePicture
                    Glide.with(profile)
                        .load(valuesProfile)
                        .into(profile)
                    itemTitle.text = item.title
                    itemTime.text = newDate
                    itemFullName.text = item.author.fullName
                    itemLike.text = " ${item.like}"
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