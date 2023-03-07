package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentAuthLoginBinding
import com.example.pklparentinghub.ui.base.LoginViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.LoginViewModel
import com.example.pklparentinghub.utils.Status

class AuthLoginFragment : Fragment() {

    private var _binding: FragmentAuthLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        loginAuth()
        initObserve()
        initTextWatcher()

        binding.loginNavigateToRegister.setOnClickListener {
            val fragment = AuthRegisterFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutAuthActivity, fragment)?.commit()
        }
    }

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LoginViewModel::class.java]
    }

    private fun loginAuth(){
        binding.loginButtonLogin.setOnClickListener {
            val email = binding.loginInputEmail.text.toString()
            val password = binding.loginInputPassword.text.toString()

            viewModel.requestLogin(email, password)
        }
    }

    private fun initObserve(){
        setupObserve()
    }
    private fun setupObserve(){
        viewModel.loginResult.observe(viewLifecycleOwner){result ->
            when (result.status){
                Status.SUCCESS -> {
                    val intentBiasa = Intent(this.context, MainActivity::class.java)
                    startActivity(intentBiasa)

                    Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                }

                Status.LOADING -> {
                    Log.e(ContentValues.TAG, "setupObservers: LOADING")
                }

                Status.ERROR -> {
                    Log.e(ContentValues.TAG, "setupObservers: ERROR")
                }
            }
        }
    }

    private fun initTextWatcher(){
        textWatcherEmail()
        textWatcherPassword()
    }

    private fun textWatcherEmail() {
        binding.loginInputEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    errorNullEmail()
                } else {
                    clearEmail()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun textWatcherPassword() {
        binding.loginInputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    errorNullPassword()
                } else {
                    clearPassword()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }


    private fun errorNullEmail(): Boolean {
        binding.loginEmail.error = getText(R.string.error_text_null_email)
        errorBorderEmail()
        return false
    }

    private fun errorNullPassword(): Boolean {
        binding.loginPassword.error = getText(R.string.error_text_null_password)
        errorBorderPassword()
        return false
    }

    private fun clearEmail(): Boolean {
        binding.loginEmail.isErrorEnabled = false
        defaultBorderEmail()
        return true
    }

    private fun clearPassword(): Boolean {
        binding.loginPassword.isErrorEnabled = false
        defaultBorderPassword()
        return true
    }

    private fun defaultBorderEmail() {
        binding.loginInputEmail.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderPassword() {
        binding.loginInputPassword.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderEmail() {
        binding.loginInputEmail.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderPassword() {
        binding.loginInputPassword.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}