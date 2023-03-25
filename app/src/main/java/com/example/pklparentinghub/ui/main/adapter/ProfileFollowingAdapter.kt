package com.example.pklparentinghub.ui.main.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.data.model.userFollowing.User
import com.example.pklparentinghub.databinding.ItemFollowingProfileBinding

class ProfileFollowingAdapter(private val listener: ProfileFollowingAdapter.OnItemClickListener): RecyclerView.Adapter<ProfileFollowingAdapter.FollowingViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: User)
    }
    inner class FollowingViewHolder(private val binding: ItemFollowingProfileBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun setData(item: User){
            binding.itemFollowingButton.setOnClickListener { listener.onItemClick(item) }

            binding.apply {
                val followerPicture = item.profilePicture
                val picture = itemFollowingPicture
                Glide.with(picture)
                    .load(followerPicture)
                    .into(picture)
                itemFollowingFullName.text = item.fullName
                itemFollowingUsername.text = item.username
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

    var items : List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FollowingViewHolder(
        ItemFollowingProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}