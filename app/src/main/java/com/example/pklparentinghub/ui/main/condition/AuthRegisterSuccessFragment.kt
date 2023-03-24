package com.example.pklparentinghub.ui.main.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentConditionSingleActionBinding
import com.example.pklparentinghub.ui.main.view.AuthLoginFragment
import com.example.pklparentinghub.ui.main.view.AuthLoginFragmentDirections

class AuthRegisterSuccessFragment : DialogFragment() {

    private var _binding: FragmentConditionSingleActionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionSingleActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOutput()
        setNavigation()
    }

    private fun setOutput(){
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.registerConditionImage.setImageResource(R.drawable.img_condition_success)
        binding.registerConditionTitle.text = "Daftar Berhasil"
        binding.registerConditionContent.text = "Anda akan ditujukan ke halaman masuk"
        binding.registerConditionButton.text = "Masuk"
    }


    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener {
            dialog!!.dismiss()
            findNavController().navigate(AuthRegisterSuccessFragmentDirections.actionAuthRegisterSuccessFragmentToAuthLoginFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}