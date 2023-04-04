package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.ui.base.ArticleDetailViewModelFactory
import com.example.pklparentinghub.ui.base.ArticleEditViewModelFactory
import com.example.pklparentinghub.ui.main.condition.CancelEditArticle
import com.example.pklparentinghub.ui.main.viewmodel.ArticleDetailViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ArticleEditViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout

class EditArticleActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailViewModel
    private lateinit var editViewModel: ArticleEditViewModel

    lateinit var etTitleArticle: EditText
    lateinit var tilTitleArticle: TextInputLayout
    lateinit var etArticle: EditText
    lateinit var btnEdit: Button
    lateinit var toolbar: MaterialToolbar

    var articleId: Int = 0
    lateinit var title: String
    lateinit var content: String
    lateinit var newTitle: String
    lateinit var newContent: String

    private var isNullTitle = false
    lateinit var filename: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_article)

        etTitleArticle = findViewById(R.id.etTitleArticle)
        tilTitleArticle = findViewById(R.id.tilTitleArticle)
        etArticle = findViewById(R.id.etArticle)
        btnEdit = findViewById(R.id.btnEditArticle)
        toolbar = findViewById(R.id.topAppBar)
        articleId = intent.getIntExtra("id", 0)

        setupViewModel()
        setupObserveDetail()
        checkTitle()
        btnEditOnClick()
        onBackClick()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ArticleDetailViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleDetailViewModel::class.java]

        editViewModel = ViewModelProvider(
            this, ArticleEditViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleEditViewModel::class.java]
    }

    private fun setupObserveDetail() {
        lifecycleScope.launchWhenCreated {
            AccessManager(this@EditArticleActivity)
                .access
                .collect { token ->
                    viewModel.getArticleDetail(token, articleId)
                        .observe(this@EditArticleActivity, Observer {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        resource.data?.data?.let { item ->
                                            title = item.title
                                            content = item.content
                                            filename = item.thumbnail.substring(61)

                                            etTitleArticle.setText(title)
                                            etArticle.setText(content)
                                        }
                                    }
                                    Status.ERROR -> {
                                        Toast.makeText(
                                            this@EditArticleActivity,
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

    private fun checkTitle() {
        etTitleArticle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 >= 1) {
                    tilTitleArticle.isErrorEnabled = false
                    etTitleArticle.setBackgroundResource(R.drawable.slr_outline_button_border)
                } else {
                    tilTitleArticle.error = getString(R.string.error_text_null_title)
                    etTitleArticle.setBackgroundResource(R.drawable.bg_white_red_outline)
                }
            }
        })
    }

    private fun isNullTitle(): Boolean {
        isNullTitle = if (etTitleArticle.length() == 0) {
            tilTitleArticle.error = getString(R.string.error_text_null_title)
            etTitleArticle.setBackgroundResource(R.drawable.bg_white_red_outline)
            false
        } else {
            true
        }
        return isNullTitle
    }


    private fun btnEditOnClick() {
        btnEdit.setOnClickListener {
            newTitle = etTitleArticle.text.toString()
            newContent = etArticle.text.toString()
            if (newContent.isEmpty()) {
                Toast.makeText(this@EditArticleActivity, getString(R.string.error_text_null_article), Toast.LENGTH_SHORT).show()
            } else if ((newContent.length < 400)) {
                Toast.makeText(this@EditArticleActivity, getString(R.string.error_text_min_article), Toast.LENGTH_SHORT).show()
            } else if (isNullTitle()) {
                lifecycleScope.launchWhenResumed {
                    AccessManager(this@EditArticleActivity)
                        .access
                        .collect { token ->
                            editViewModel.editArticle(
                                token, articleId, ArticleRequest(
                                    title = newTitle,
                                    content = newContent,
                                    thumbnail = filename
                                )
                            )
                        }
                }
                setupObserveEditArticle()
            }
        }
    }

    private fun setupObserveEditArticle() {
        editViewModel.editResult.observe(this@EditArticleActivity, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(this@EditArticleActivity, "Artikel Berhasil di Edit", Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@EditArticleActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this@EditArticleActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun onBackClick() {
        toolbar.setNavigationOnClickListener {
            validationSame()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        validationSame()
    }

    private fun validationSame() {
        newTitle = etTitleArticle.text.toString()
        newContent = etArticle.text.toString()
        if (title == newTitle && content == newContent) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val dialog = CancelEditArticle()
            dialog.show(supportFragmentManager, "cancelEdit")
        }
    }
}