package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pklparentinghub.databinding.FragmentMainProfileBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentMainProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayoutMainActivity, MainProfileFragment()).commit()
    }
}