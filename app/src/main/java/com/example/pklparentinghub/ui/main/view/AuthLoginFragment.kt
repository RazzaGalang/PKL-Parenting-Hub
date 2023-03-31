package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentAuthLoginBinding
import com.example.pklparentinghub.ui.base.LoginViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.LoginViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

        initTextWatcher()
        setupForm()
        setupViewModel()
        setupObserve()
        setupToRegister()
    }

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[LoginViewModel::class.java]
    }

    private fun setupForm(){
        binding.loginButtonLogin.setOnClickListener {
            if (!checkingValidate()) {
                validateForm()
            } else {
                setupRequest()
            }
        }
    }

    private fun setupRequest(){
        viewModel.requestLogin(
            binding.loginInputEmail.text.toString(),
            binding.loginInputPassword.text.toString()
        )
    }

    private fun setupObserve(){
        viewModel.loginResult.observe(viewLifecycleOwner){result ->
            when (result.status){
                Status.SUCCESS -> {
                    result.data?.body()?.data?.let {
                        AccessManager(requireContext())
                            .setAccess(it.token, lifecycleScope)
                    }

                    if(result.data?.body()?.data?.user?.verifikasi!!)
                        findNavController().navigate(AuthLoginFragmentDirections.actionAuthLoginFragmentToMainActivity())
                    else
                        findNavController().navigate(AuthLoginFragmentDirections.actionAuthLoginFragmentToCompleteProfileOnBoardingFragment())

                    Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                }

                Status.LOADING -> {
                    Log.e(ContentValues.TAG, "setupObservers: LOADING")
                }

                Status.ERROR -> {
                    Log.e(ContentValues.TAG, "setupObservers: ERROR")

                    val type = object : TypeToken<List<String>>() {}.type
                    val errors = Gson().fromJson<List<String>>(result.message, type)

                    binding.apply {
                        loginEmail.error = errors.find { it.contains("email") }
                        loginPassword.error = errors.find { it.contains("password") }
                    }
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
                validateEmail()
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
                validatePassword()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun checkingValidate(): Boolean {
        return !(!validateEmail() || !validatePassword())
    }

    private fun validateForm() {
        validateEmail()
        validatePassword()
    }

    private fun validateEmail() : Boolean {
        return if (binding.loginInputEmail.length() == 0) {
            errorNullEmail()
            false
        } else {
            clearEmail()
            true
        }
    }

    private fun validatePassword() : Boolean {
        return if (binding.loginInputPassword.length() == 0) {
            errorNullPassword()
            false
        } else {
            clearPassword()
            true
        }
    }

    private fun errorNullEmail() {
        binding.loginEmail.error = getText(R.string.error_text_null_email)
        errorBorderEmail()
    }

    private fun errorNullPassword() {
        binding.loginPassword.error = getText(R.string.error_text_null_password)
        errorBorderPassword()
    }

    private fun clearEmail() {
        binding.loginEmail.isErrorEnabled = false
        defaultBorderEmail()
    }

    private fun clearPassword() {
        binding.loginPassword.isErrorEnabled = false
        defaultBorderPassword()
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

    private fun setupToRegister(){
        binding.loginNavigateToRegister.setOnClickListener{
            findNavController().navigate(AuthLoginFragmentDirections.actionAuthLoginFragmentToAuthRegisterFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}