package com.example.pklparentinghub.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pklparentinghub.R

class CreateArticleActivity : AppCompatActivity(){

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_article)

        setNavigation()
    }

    private fun setNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_create_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.fragmentCreateArticle)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}