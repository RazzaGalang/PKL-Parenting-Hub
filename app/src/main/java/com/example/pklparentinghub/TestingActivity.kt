package com.example.pklparentinghub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.ui.main.view.AuthLoginFragment
import com.example.pklparentinghub.ui.main.view.AuthOnBoardingFragment

class TestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)

        loadFragment(AuthOnBoardingFragment())
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutTestingActivity,fragment)
        transaction.commit()
    }
}