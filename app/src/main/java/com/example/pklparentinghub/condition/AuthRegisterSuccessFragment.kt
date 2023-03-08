package com.example.pklparentinghub.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentAuthRegisterConditionBinding
import com.example.pklparentinghub.ui.main.view.AuthLoginFragment

class AuthRegisterSuccessFragment : DialogFragment() {

    private var _binding: FragmentAuthRegisterConditionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthRegisterConditionBinding.inflate(inflater, container, false)
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
            val fragment = AuthLoginFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutAuthActivity, fragment)?.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}