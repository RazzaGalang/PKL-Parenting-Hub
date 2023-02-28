package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.databinding.ActivityMainBinding
import com.example.pklparentinghub.databinding.FragmentProfileBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(FragmentProfile())
    }

    private fun replaceFragment(profileFragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,profileFragment)
        fragmentTransaction.commit()
    }
}