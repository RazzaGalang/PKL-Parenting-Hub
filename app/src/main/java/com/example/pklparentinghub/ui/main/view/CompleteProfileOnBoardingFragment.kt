package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.databinding.FragmentAuthOnBoardingBinding

class CompleteProfileOnBoardingFragment : Fragment() {

    private var _binding: FragmentAuthOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthOnBoardingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOutput()
        setNavigation()
    }

    private fun setOutput(){
        binding.apply {
            onBoardingTitleText.text = "Selamat Datang, Parents!"
            onBoardingContentText.text = "Halo Parents di seluruh dunia. Sebelum mulai, yuk lengkapi profile parents!"
            onBoardingButtonContinue.text = "Lengkapi Profile"
        }
    }

    private fun setNavigation(){
        binding.onBoardingButtonContinue.setOnClickListener {
            val cobaGeys = Intent(this.context, MainActivity::class.java)
            startActivity(cobaGeys)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}