package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class SettingActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setNavigation()
    }

    private fun setNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navSettingContainer) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.settingDetailFragment)

        if (!navController.popBackStack()) finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}