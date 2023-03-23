package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.data.model.userFollow.User
import com.example.pklparentinghub.databinding.ItemFollowersProfileBinding

class ProfileFollowersAdapter: RecyclerView.Adapter<ProfileFollowersAdapter.FollowerViewHolder>() {
    inner class FollowerViewHolder(private val binding: ItemFollowersProfileBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun setData(item: User){
                    binding.apply {
                        val followerPicture = item.profilePicture
                        val picture = itemFollowersPicture
                        Glide.with(picture)
                            .load(followerPicture)
                            .into(picture)
                        itemFollowersFullName.text = item.fullName
                        itemFollowersUsername.text = item.username
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

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size

    var items : List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FollowerViewHolder(
        ItemFollowersProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}