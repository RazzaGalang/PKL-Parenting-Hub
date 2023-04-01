package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.model.articleData.Article
import com.example.pklparentinghub.databinding.ItemArticleProfileBinding
import com.example.pklparentinghub.databinding.ItemSearchArticleBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class ArticleSearchAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<ArticleSearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Article)
        fun onItemLike(item: Article)
    }

    inner class ViewHolder (private val binding: ItemSearchArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Article) {
            binding.apply {
                clArticle.setOnClickListener{listener.onItemClick(item)}
                itemIconLike.setOnClickListener{listener.onItemLike(item)}

                val isLiked = item.isLiked
                if (isLiked) itemIconLike.setImageResource(R.drawable.ic_like_dark)
                else itemIconLike.setImageResource(R.drawable.ic_like)

                val date = item.createdAt.substring(0, 10)
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
                val newDate: String = dateFormat.format(df.parse(date))
                val valuesThumbnail = item.thumbnail
                val thumbnail = itemCover
                Glide.with(thumbnail)
                    .load(valuesThumbnail)
                    .into(thumbnail)
                val valuesProfile = item.author.profilePicture
                val profile = itemProfile
                if (valuesProfile == "https://parenting-lite-api.intern.paninti.com/storage/images/default-profile.png"){
                    profile.setImageResource(R.drawable.img_profile_default_picture)
                } else {
                    Glide.with(profile)
                        .load(valuesProfile)
                        .into(profile)
                }
                itemTitle.text = item.title
                itemTime.text = newDate
                itemFullName.text = item.author.fullName
                itemLike.text = "${item.like} Suka"
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
        ItemSearchArticleBinding.inflate(
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