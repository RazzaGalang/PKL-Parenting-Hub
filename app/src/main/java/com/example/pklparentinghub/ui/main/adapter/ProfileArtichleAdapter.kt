package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.data.model.profile.ProfileModel
import com.example.pklparentinghub.databinding.ItemArticleProfileBinding

class ProfileArtichleAdapter: RecyclerView.Adapter<ProfileArtichleAdapter.ProfileViewHolder>() {
    inner class ProfileViewHolder(private val binding: ItemArticleProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileModel) {
            binding.apply{
                itemProfilePicture.setImageResource(item.ivProfile)
                itemProfileUsername.text = item.username
                itemProfileDescription.text = item.desc
                itemProfileLike.text = item.like
                itemProfileDate.text = item.date
                itemProfileDefaultCover.setImageResource(item.default)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ProfileModel>() {
        override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ProfileArtichleAdapter.ProfileViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileArtichleAdapter.ProfileViewHolder {
        val listArtichleBinding = ItemArticleProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(listArtichleBinding)
    }
}