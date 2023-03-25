package com.example.pklparentinghub.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pklparentinghub.R
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerProfileFollowAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_COUNT = 5
    private var showShimmer = true

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_shimmer_follower_profile, parent, false)
        return ShimmerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShimmerArticleHomeAdapter.ShimmerViewHolder) {
            if (showShimmer) {
                holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout).startShimmer()
            } else {
                holder.itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout).stopShimmer()
            }
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    fun setShowShimmer(show: Boolean) {
        showShimmer = show
        notifyDataSetChanged()
    }
}