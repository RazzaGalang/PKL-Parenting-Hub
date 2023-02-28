package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.shimmer.ShimmerProfileFragment
import com.example.pklparentinghub.ui.main.view.MainEventFragment
import com.example.pklparentinghub.ui.main.view.MainHomeFragment
import com.example.pklparentinghub.ui.main.view.MainNotificationFragment
import com.example.pklparentinghub.ui.main.view.MainProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(MainHomeFragment())
        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuBottomNavigationHome -> {
                    loadFragment(MainHomeFragment())
                    true
                }
                R.id.menuBottomNavigationEvent -> {
                    loadFragment(MainEventFragment())
                    true
                }
                R.id.menuBottomNavigationCreateArticle -> {
                    loadFragment(MainEventFragment())
                    true
                }
                R.id.menuBottomNavigationNotification -> {
                    loadFragment(MainNotificationFragment())
                    true
                }
                R.id.menuBottomNavigationProfile -> {
                    loadFragment(MainProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutMainActivity,fragment)
        transaction.commit()
    }
}