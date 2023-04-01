package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.articleData.Article
import com.example.pklparentinghub.ui.base.ArticleAllViewModelFactory
import com.example.pklparentinghub.ui.base.ArticleDetailViewModelFactory
import com.example.pklparentinghub.ui.base.LikeModelFactory
import com.example.pklparentinghub.ui.main.adapter.ArticleSearchAdapter
import com.example.pklparentinghub.ui.main.adapter.ShimmerArticleHomeAdapter
import com.example.pklparentinghub.ui.main.viewmodel.ArticleAllViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ArticleDetailViewModel
import com.example.pklparentinghub.ui.main.viewmodel.LikeViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.appbar.MaterialToolbar
import java.text.DateFormat
import java.text.SimpleDateFormat

class DetailArticleActivity : AppCompatActivity(), ArticleSearchAdapter.OnItemClickListener {

    var articleId: Int = 0
    lateinit var cover: ImageView
    lateinit var title: TextView
    lateinit var profile: ImageView
    lateinit var fullName: TextView
    lateinit var time: TextView
    lateinit var article: TextView
    lateinit var like: TextView
    lateinit var iconLike: ImageView
    lateinit var rvArticle: RecyclerView
    lateinit var rvArticleLoading: RecyclerView
    lateinit var toolbar: MaterialToolbar

    private lateinit var viewModel: ArticleDetailViewModel
    private lateinit var articleViewModel: ArticleAllViewModel
    private lateinit var likeViewModel: LikeViewModel

    private val adapter = ArticleSearchAdapter(this)
    private val shimmerAdapter: ShimmerArticleHomeAdapter = ShimmerArticleHomeAdapter()

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
        iconLike = findViewById(R.id.itemIconLike)
        rvArticle = findViewById(R.id.rvArticleAnother)
        rvArticleLoading = findViewById(R.id.rvArticleAnotherLoading)
        toolbar = findViewById(R.id.topAppBar)
        articleId = intent.getIntExtra("id", 1)

        initListView()
        setupViewModel()
        setupObserveDetailArticle()
        setupObserveAnotherArticle()
        onBackClick()
    }

    override fun onItemClick(item: Article) {
        val intent = Intent(this, DetailArticleActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }

    override fun onItemLike(item: Article) {
        setupLike(item)
    }

    private fun initListView() {
        rvArticle.adapter = adapter
        rvArticle.addItemDecoration(
            DividerItemDecoration(
                rvArticle.context,
                (rvArticle.layoutManager as LinearLayoutManager).orientation
            )
        )
        rvArticleLoading.adapter = shimmerAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleDetailViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleDetailViewModel::class.java]

        articleViewModel = ViewModelProvider(
            this, ArticleAllViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleAllViewModel::class.java]

        likeViewModel = ViewModelProvider(
            this,
            LikeModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LikeViewModel::class.java]
    }

    private fun showLoading(loading: Boolean) {
        val detail: ScrollView = findViewById(R.id.svDetail)
        val detailLoading: ShimmerFrameLayout = findViewById(R.id.DetailArticleLoading)

        detailLoading.isVisible = loading
        detail.isVisible = !loading
    }

    private fun setupObserveDetailArticle() {
        lifecycleScope.launchWhenCreated {
            AccessManager(this@DetailArticleActivity)
                .access
                .collect { token ->
                    viewModel.getArticleDetail(token, articleId)
                        .observe(this@DetailArticleActivity, Observer {
                            it?.let { resource ->
                                showLoading(resource.status == Status.LOADING)
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        resource.data?.data?.let { item ->

                                            val isLiked = item.isLiked
                                            if (isLiked) iconLike.setImageResource(R.drawable.ic_like_dark)
                                            else iconLike.setImageResource(R.drawable.ic_like)

                                            val valuesCover = item.thumbnail
                                            val valuesProfile = item.author.profilePicture
                                            if (valuesProfile == "https://parenting-lite-api.intern.paninti.com/storage/images/default-profile.png") {
                                                profile.setImageResource(R.drawable.img_profile_default_picture)
                                            } else {
                                                Glide.with(profile)
                                                    .load(valuesProfile)
                                                    .into(profile)
                                            }
                                            Glide.with(cover)
                                                .load(valuesCover)
                                                .into(cover)
                                            val date = item.createdAt.substring(0, 10)
                                            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                                            val dateFormat: DateFormat =
                                                SimpleDateFormat("dd MMMM yyyy")
                                            val newDate: String = dateFormat.format(df.parse(date))
                                            val valuesLike = item.like.toString()
                                            time.text = newDate
                                            title.text = item.title
                                            fullName.text = item.author.fullName
                                            like.text = "$valuesLike Suka"
                                            article.text = item.content

                                        }
                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(
                                            this@DetailArticleActivity,
                                            it.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    Status.LOADING -> {}
                                }
                            }
                        })
                }
        }
    }

    private fun setupObserveAnotherArticle() {
        lifecycleScope.launchWhenCreated {
            AccessManager(this@DetailArticleActivity)
                .access
                .collect { token ->
                    articleViewModel.getArticleAll(token, 4, "", true, true)
                        .observe(this@DetailArticleActivity, Observer {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        resource.data.let { article ->
                                            adapter.items = article!!.data.article
                                        }
                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(
                                            this@DetailArticleActivity,
                                            it.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    Status.LOADING -> {}
                                }
                            }
                        })
                }
        }
    }

    private fun setupLike(item: Article) {
        lifecycleScope.launchWhenResumed {
            AccessManager(this@DetailArticleActivity)
                .access
                .collect { token ->
                    likeViewModel.postUserLike(token, item.id)
                        .observe(this@DetailArticleActivity, Observer {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        setupObserveAnotherArticle()
                                    }
                                    Status.LOADING -> {}
                                    Status.ERROR -> {
                                        Toast.makeText(this@DetailArticleActivity, "Error" + it.message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        })
                }
        }
    }

    private fun onBackClick() {
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}