package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pklparentinghub.databinding.ActivityMainBinding
import com.example.pklparentinghub.databinding.FragmentProfileBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentProfile()).commit()
        
    }
}