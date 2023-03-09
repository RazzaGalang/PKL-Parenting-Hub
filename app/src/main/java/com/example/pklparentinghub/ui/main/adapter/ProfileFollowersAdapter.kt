package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.data.model.profile.ProfileConnectionModel
import com.example.pklparentinghub.databinding.ItemFollowersProfileBinding

class ProfileFollowersAdapter: RecyclerView.Adapter<ProfileFollowersAdapter.ConnectionViewHolder>() {
    inner class ConnectionViewHolder(private val binding: ItemFollowersProfileBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun bind(item: ProfileConnectionModel){
                    binding.apply {
                        itemFollowersPicture.setImageResource(item.ivProfile)
                        itemFollowersFullName.text = item.name
                        itemFollowersUsername.text = item.username
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

    override fun onBindViewHolder(holder: ProfileFollowersAdapter.ConnectionViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileFollowersAdapter.ConnectionViewHolder {
        val listFollowersBinding = ItemFollowersProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConnectionViewHolder(listFollowersBinding)
    }
}