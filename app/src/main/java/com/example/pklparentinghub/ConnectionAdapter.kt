package com.example.pklparentinghub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.databinding.ListFollowersBinding

class ConnectionAdapter: RecyclerView.Adapter<ConnectionAdapter.ConnectionViewHolder>() {
    inner class ConnectionViewHolder(private val binding: ListFollowersBinding) :
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

    override fun onBindViewHolder(holder: ConnectionAdapter.ConnectionViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectionAdapter.ConnectionViewHolder {
        val listFollowersBinding = ListFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConnectionViewHolder(listFollowersBinding)
    }
}