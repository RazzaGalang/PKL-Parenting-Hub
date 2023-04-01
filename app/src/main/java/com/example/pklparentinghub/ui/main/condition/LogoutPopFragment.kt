package com.example.pklparentinghub.ui.main.condition

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentConditionDuoActionBinding
import com.example.pklparentinghub.ui.base.LogoutViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.LogoutViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status

class LogoutPopFragment : DialogFragment () {
    private var _binding: FragmentConditionDuoActionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LogoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionDuoActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOutput()
        setNavigation()
        setupViewModel()
        onLogoutObserve()
    }

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this,
            LogoutViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LogoutViewModel::class.java]
    }

    private fun setOutput(){
        dialog?.setCancelable(false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.registerConditionImage.setImageResource(R.drawable.img_condition_logout)
        binding.registerConditionTitle.text = "Keluar"
        binding.registerConditionContent.text = "Anda yakin akan keluar akun?"
        binding.registerConditionButton.text = "Keluar"
        binding.secondConditionButton.text = "Kembali"

        binding.registerConditionButton.backgroundTintList = context?.getColorStateList(R.color.error30)
        binding.registerConditionButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun setNavigation(){
        binding.registerConditionButton.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                AccessManager(requireContext())
                    .access
                    .collect { token ->
                        viewModel.requestLogout(token)
                    }
            }
        }

        binding.secondConditionButton.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    private fun onLogoutObserve(){
        viewModel.logoutResult.observe(viewLifecycleOwner){ result ->
            when (result.status){
                Status.LOADING -> {
                    Log.e(TAG, "onLogoutObserve: STATUS LOGOUT LOADING")
                }
                Status.SUCCESS -> {
                    Log.e(TAG, "onLogoutObserve: STATUS LOGOUT ERROR")
                    lifecycleScope.launchWhenResumed {
                        AccessManager(requireContext())
                            .clearAccess()
                    }

                    lifecycleScope.launchWhenResumed {
                        AccessManager(requireContext())
                            .clearUserId()
                    }

                    activity?.finish()
                    startActivity(activity?.intent)
                }
                Status.ERROR -> {
                    Log.e(TAG, "onLogoutObserve: STATUS LOGOUT ERROR")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}