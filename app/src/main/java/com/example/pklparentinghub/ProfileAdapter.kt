package com.example.pklparentinghub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.databinding.ListArtichleBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(private val binding: ListArtichleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileModel) {
            binding.apply {
                ivProf.setImageResource(item.ivProfile)
                tvUser.text = item.username
                tvDesc.text = item.desc
                icLike.setImageResource(item.icLike)
                tvLike.text = item.like
                tvDate.text = item.date
                ivDefault.setImageResource(item.default)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ProfileModel>() {
        override fun areItemsTheSame(
            oldItem: ProfileModel,
            newItem: ProfileModel
        ) = oldItem.username == newItem.username

        override fun areContentsTheSame(
            oldItem: ProfileModel,
            newItem: ProfileModel
        ) = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileAdapter.ProfileViewHolder {
        val listArtichleBinding =
            ListArtichleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(listArtichleBinding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }
}