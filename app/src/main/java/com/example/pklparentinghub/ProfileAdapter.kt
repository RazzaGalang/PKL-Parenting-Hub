package com.example.pklparentinghub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pklparentinghub.databinding.FragmentProfileBinding
import com.example.pklparentinghub.databinding.ListArtichleBinding
import com.google.android.material.imageview.ShapeableImageView
import java.text.FieldPosition

class ProfileAdapter(private val profileList : ArrayList<ProfileModel>): RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_artichle, parent, false)
        return ProfileViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val currentItem = profileList[position]
        holder.ivProf.setImageResource(currentItem.ivProfile)
        holder.tvUser.text = currentItem.Username
        holder.tvDesc.text = currentItem.Desc
        holder.icLike.setImageResource(currentItem.icLike)
        holder.tvLike.text = currentItem.Like
        holder.tvDate.text = currentItem.Date
        holder.ivDefault.setImageResource(currentItem.Default)
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val ivProf : ShapeableImageView = itemView.findViewById(R.id.ivProf)
        val tvUser : TextView = itemView.findViewById(R.id.tvUser)
        val tvDesc : TextView = itemView.findViewById(R.id.tvDesc)
        val icLike : ShapeableImageView = itemView.findViewById(R.id.icLike)
        val tvLike : TextView = itemView.findViewById(R.id.tvLike)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val ivDefault : ShapeableImageView = itemView.findViewById(R.id.ivDefault)

    }
//    private lateinit var binding: ListArtichleBinding
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder{
//        binding = ListArtichleBinding.inflate(LayoutInflater.from(parent.context), parent,false)
//
//        return ViewHolder()
//    }
//
//    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
//        holder.setData(differ.currentList[position])
//        holder.setIsRecyclable(false)
//    }
//
//    override fun getItemCount()=differ.currentList.size
//
//    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
//        fun setData(item : ProfileModel){
//            binding.apply {
//                ivProf.setImageResource(item.ivProfile)
//                tvUser.text = item.Username
//                tvDesc.text = item.Desc
//                icLike.setImageResource(item.icLike)
//                tvLike.text = item.Like
//                tvDate.text = item.Date
//                ivDefault.setImageResource(item.Default)
//            }
//        }
//
//    }
//
//    private val differCallback = object : DiffUtil.ItemCallback<ProfileModel>(){
//        override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
//            return oldItem.Username == newItem.Username
//        }
//
//        @SuppressLint("DiffUtilEquals")
//        override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
//            return oldItem == newItem
//        }
//    }
//    val differ = AsyncListDiffer(this,differCallback)
}