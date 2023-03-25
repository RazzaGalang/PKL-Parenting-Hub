package com.example.pklparentinghub.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.ui.base.ArticleDetailViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.ArticleDetailViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import java.text.DateFormat
import java.text.SimpleDateFormat

class DetailArticleActivity : AppCompatActivity() {

    var articleId: Int = 0
    lateinit var cover: ImageView
    lateinit var title: TextView
    lateinit var profile: ImageView
    lateinit var fullName: TextView
    lateinit var time: TextView
    lateinit var article: TextView
    lateinit var like: TextView
    private lateinit var viewModel : ArticleDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        cover = findViewById(R.id.itemCover)
        title = findViewById(R.id.itemTitle)
        profile = findViewById(R.id.itemProfilePicture)
        fullName = findViewById(R.id.itemFullName)
        time = findViewById(R.id.itemTime)
        article = findViewById(R.id.itemArticle)
        like = findViewById(R.id.itemLike)

        articleId = intent.getIntExtra("id", 1)
        setupViewModel()
        requestWithToken()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleDetailViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleDetailViewModel::class.java]
    }

    private fun requestWithToken() {
        lifecycleScope.launchWhenCreated {
            AccessManager(this@DetailArticleActivity)
                .access
                .collect { token ->
                    viewModel.getArticleDetail(token, articleId)
                        .observe(this@DetailArticleActivity, Observer {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        resource.data?.data?.let {item ->
                                            val valuesCover = item.thumbnail
                                            val valuesProfile = item.author.profilePicture
                                            Glide.with(cover)
                                                .load(valuesCover)
                                                .into(cover)
                                            Glide.with(profile)
                                                .load(valuesProfile)
                                                .into(profile)
                                            val date = item.createdAt.substring(0, 9)
                                            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                                            val dateFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
                                            val newDate: String = dateFormat.format(df.parse(date))
                                            val valuesLike = item.like.toString()
                                            time.text = newDate
                                            title.text = item.title
                                            fullName.text = item.author.fullName
                                            like.text = " $valuesLike"
                                            article.text = item.content
                                        }
                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(this@DetailArticleActivity, it.message , Toast.LENGTH_LONG).show()
                                    }
                                    Status.LOADING -> {}
                                }
                            }
                        })
                }
        }
    }
}