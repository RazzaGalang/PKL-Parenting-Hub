package com.example.pklparentinghub.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pklparentinghub.R

class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }

    private fun setNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navAuthContainer) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.authOnBoardingFragment)

        if (!navController.popBackStack()) finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}