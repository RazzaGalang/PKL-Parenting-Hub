package com.example.pklparentinghub.ui.main.condition

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentConditionDoubleActionBinding
import com.example.pklparentinghub.ui.base.ArticleDeleteViewModelFactory
import com.example.pklparentinghub.ui.main.view.MainActivity
import com.example.pklparentinghub.ui.main.viewmodel.ArticleDeleteViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class WarningDeleteArticle(val articleId: Int) : DialogFragment() {

    private lateinit var viewModel : ArticleDeleteViewModel
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

        setupViewModel()
        setOutput()
        setNavigation()
    }

    private fun setOutput(){
        dialog?.setCancelable(true)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.apply {
            registerConditionImage.setImageResource(R.drawable.img_condition_delete_article)
            registerConditionTitle.text = "Hapus Artikel"
            registerConditionContent.text = "Anda yakin ingin menghapus artikel ini?"
            registerConditionButton.text = "Hapus"
            registerConditionButton.setBackgroundColor(requireContext().getColor(R.color.error30))
            registerConditionButton.setTextColor(requireContext().getColor(R.color.white))
            registerConditionButton2.text = "Kembali"
        }
    }

    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener {
            setupObserver()
        }
        binding.registerConditionButton2.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    private fun setupViewModel () {
        viewModel = ViewModelProvider(
            this, ArticleDeleteViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ArticleDeleteViewModel::class.java]
    }

    private fun setupObserver(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModel.deleteArticle(token, articleId)
                }
        }

        viewModel.deleteResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Artikel Berhasil di Hapus", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}