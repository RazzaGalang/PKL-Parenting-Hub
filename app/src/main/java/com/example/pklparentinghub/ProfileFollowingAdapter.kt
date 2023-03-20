package com.example.pklparentinghub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.databinding.ItemFollowingProfileBinding

class ProfileFollowingAdapter: RecyclerView.Adapter<ProfileFollowingAdapter.ConnectionFollowingViewHolder>() {
    inner class ConnectionFollowingViewHolder(private val binding: ItemFollowingProfileBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProfileConnectionModel){
            binding.apply {
                itemFollowingPicture.setImageResource(item.ivProfile)
                itemFollowingFullName.text = item.name
                itemFollowingUsername.text = item.username
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ProfileConnectionModel>(){
        override fun areItemsTheSame(oldItem: ProfileConnectionModel, newItem: ProfileConnectionModel): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: ProfileConnectionModel, newItem: ProfileConnectionModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ProfileFollowingAdapter.ConnectionFollowingViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileFollowingAdapter.ConnectionFollowingViewHolder {
        val listFollowingBinding = ItemFollowingProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConnectionFollowingViewHolder(listFollowingBinding)
    }
}