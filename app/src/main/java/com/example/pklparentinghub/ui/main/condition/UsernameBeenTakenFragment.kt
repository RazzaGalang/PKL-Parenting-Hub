package com.example.pklparentinghub.ui.main.condition

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentConditionSingleActionBinding

class UsernameBeenTakenFragment : DialogFragment() {

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
        dialog?.setCancelable(false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.registerConditionImage.setImageResource(R.drawable.img_condition_username_already_taken)
        binding.registerConditionTitle.text = "Coba Lagi"
        binding.registerConditionContent.text = "Nama Pengguna yang Anda Masukkan Tidak Tersedia"
        binding.registerConditionButton.text = "Kembali"
    }


    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}