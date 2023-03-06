package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreateArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_article)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerCreateArticle, FragmentCreateArticle())
        transaction.commit()
    }
}