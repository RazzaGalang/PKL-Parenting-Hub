package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNavigationView) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuBottomNavigationHome -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.menuBottomNavigationEvent -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.menuBottomNavigationCreateArticle -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.menuBottomNavigationNotification -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.menuBottomNavigationProfile -> {
                    loadFragment(ProfileFragment())
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