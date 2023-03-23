package com.example.pklparentinghub.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pklparentinghub.R

class CreateArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_article)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerCreateArticle, FragmentCreateArticle())
        transaction.commit()
    }
}