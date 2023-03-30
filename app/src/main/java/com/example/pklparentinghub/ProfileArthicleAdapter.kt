package com.example.pklparentinghub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.databinding.ItemArthicleProfileBinding

class ProfileArthicleAdapter: RecyclerView.Adapter<ProfileArthicleAdapter.ProfileViewHolder>() {
    inner class ProfileViewHolder(private val binding: ItemArthicleProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileModel) {
            binding.apply{
                itemProfilePicture.setImageResource(item.ivProfile)
                itemProfileUsername.text = item.username
                itemProfileDescription.text = item.desc
//                itemProfileIconLike.(item.icLike)
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

    override fun onBindViewHolder(holder: ProfileArthicleAdapter.ProfileViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileArthicleAdapter.ProfileViewHolder {
        val listArtichleBinding = ItemArthicleProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(listArtichleBinding)
    }
}