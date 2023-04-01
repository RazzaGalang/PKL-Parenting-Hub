package com.example.pklparentinghub.ui.main.condition

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentConditionDoubleActionBinding
import com.example.pklparentinghub.ui.main.view.MainActivity

class CancelCreateArticle : DialogFragment () {
    private var _binding: FragmentConditionDoubleActionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionDoubleActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOutput()
        setNavigation()
    }

    private fun setOutput(){
        dialog?.setCancelable(true)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.apply {
            registerConditionImage.setImageResource(R.drawable.img_condition_save_article)
            registerConditionTitle.text = "Kembali ke Beranda"
            registerConditionContent.text = "Artikel yang ditulis tidak akan disimpan"
            registerConditionButton.text = "Kembali ke Beranda"
            registerConditionButton.setBackgroundColor(requireContext().getColor(R.color.error30))
            registerConditionButton.setTextColor(requireContext().getColor(R.color.white))
            registerConditionButton2.text = "Lanjutkan Menulis"
        }
    }

    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.registerConditionButton2.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}