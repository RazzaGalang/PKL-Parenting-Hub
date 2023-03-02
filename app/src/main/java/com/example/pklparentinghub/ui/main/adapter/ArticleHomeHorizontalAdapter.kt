package com.example.pklparentinghub.ui.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R

class ArticleHomeHorizontalAdapter(private val context: Context, private val imageUrls: List<Int>, private val title: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageArticle = ImageView(context)
        imageArticle.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(context).load(imageUrls[position]).into(imageArticle)

        val gradientOverlay = ImageView(context)
        gradientOverlay.setImageResource(R.drawable.style_article_slider)

        val imageDescription = TextView(context)
        imageDescription.text = title[position]

        val frameLayout = FrameLayout(context)
        frameLayout.addView(imageArticle)
        frameLayout.addView(gradientOverlay)
        frameLayout.addView(imageDescription)

        container.addView(frameLayout)
        return frameLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}