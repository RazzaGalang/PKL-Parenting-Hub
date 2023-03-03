package com.example.pklparentinghub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.databinding.ListFollowingBinding

class ConnectionFollowingAdapter: RecyclerView.Adapter<ConnectionFollowingAdapter.ConnectionFollowingViewHolder>() {
    inner class ConnectionFollowingViewHolder(private val binding: ListFollowingBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: ConnectionModel){
            binding.apply {
                ivProfile.setImageResource(item.ivProfile)
                tvName.text = item.name
                tvUsername.text = item.username
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ConnectionModel>(){
        override fun areItemsTheSame(oldItem: ConnectionModel, newItem: ConnectionModel): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: ConnectionModel, newItem: ConnectionModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ConnectionFollowingAdapter.ConnectionFollowingViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectionFollowingAdapter.ConnectionFollowingViewHolder {
        val listFollowingBinding = ListFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConnectionFollowingViewHolder(listFollowingBinding)
    }
}