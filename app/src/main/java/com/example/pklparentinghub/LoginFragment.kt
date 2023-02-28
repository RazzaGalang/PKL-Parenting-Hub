package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.repository.LoginRepository
import com.example.pklparentinghub.databinding.FragmentLoginBinding
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupViewModel(){
        val apiService = RetrofitBuilder.getRetrofit()
        val loginRepository = LoginRepository(apiService)

        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LoginViewModel::class.java]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}