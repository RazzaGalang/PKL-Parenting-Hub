package com.example.pklparentinghub.ui.main.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.model.userFollower.User
import com.example.pklparentinghub.databinding.ItemFollowersProfileBinding

class ProfileFollowersAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<ProfileFollowersAdapter.FollowerViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: User)
    }
    inner class FollowerViewHolder(private val binding: ItemFollowersProfileBinding) :
            RecyclerView.ViewHolder(binding.root){
                @SuppressLint("ResourceAsColor")
                fun setData(item: User){
                    binding.itemFollowersButton.setOnClickListener { listener.onItemClick(item) }

                    binding.apply {
                        val followerPicture = item.profilePicture
                        val picture = itemFollowersPicture
                        Glide.with(picture)
                            .load(followerPicture)
                            .into(picture)
                        itemFollowersFullName.text = item.fullName
                        itemFollowersUsername.text = item.username
                        val isFollowing = item.isFollowings
                        if (isFollowing) {
                            itemFollowersButton.setBackgroundColor(R.color.primary40)
                            itemFollowersButton.strokeColor = ColorStateList.valueOf(Color.parseColor("#7A79A8"))
                            itemFollowersButton.text = root.context.getString(R.string.connec_followers)
                            itemFollowersButton.setTextColor(Color.parseColor("#FFFFFF"))
                        } else {
                            itemFollowersButton.strokeColor = ColorStateList.valueOf(Color.parseColor("#7A79A8"))
                            itemFollowersButton.text = root.context.getString(R.string.connec_follow)
                            itemFollowersButton.setTextColor(Color.parseColor("#7A79A8"))
                        }
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