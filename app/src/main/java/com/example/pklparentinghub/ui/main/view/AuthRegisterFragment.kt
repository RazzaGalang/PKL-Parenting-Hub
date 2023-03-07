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
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentAuthRegisterBinding
import com.example.pklparentinghub.ui.base.LoginViewModelFactory
import com.example.pklparentinghub.ui.base.RegisterViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.LoginViewModel
import com.example.pklparentinghub.ui.main.viewmodel.RegisterViewModel
import com.example.pklparentinghub.utils.Status
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AuthRegisterFragment : Fragment() {

    private var _binding: FragmentAuthRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : RegisterViewModel

    private val regexMinUsername = "^.{6,}$"
    private val regexMinFullname = "^.{3,}$"
    private val regexOnlyCharacter = "[A-Za-z '-]+"
    private val regexPassword = "^(?=.*\\d)[A-Za-z\\d]{8,}$"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextWatcher()
        setupViewModel()
        coba()
        initObserve()
    }

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[RegisterViewModel::class.java]

        coba()
    }

    private fun coba (){
        binding.registerButtonContinue.setOnClickListener {
            viewModel.requestRegister(
                fullname = binding.registerInputFullName.text.toString(),
                username = binding.registerInputUserName.text.toString(),
                email = binding.registerInputEmail.text.toString(),
                password = binding.registerInputPassword.text.toString(),
                confirmPassword = binding.registerInputConfirmPassword.text.toString())
        }
    }

    private fun initObserve(){
        setupObserve()
    }

    private fun setupObserve() {
        viewModel.registerResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                    }
                    Status.ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(ContentValues.TAG, "setupObservers: " + it.message)
                    }
                    Status.LOADING -> {
                        Log.e(ContentValues.TAG, "setupObservers: LOADING")
                    }
                }
            }
        })
    }

    private fun initTextWatcher(){
        textWatcherFullName()
        textWatcherUserName()
        textWatcherEmail()
        textWatcherPassword()
        textWatcherConfirmPassword()
    }

    private fun textWatcherFullName() {
        binding.registerInputFullName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    errorNullFullName()
                } else if (!(s.toString().matches(regexMinFullname.toRegex()))){
                    errorMinFullname ()
                } else if (!(s.toString().matches(regexOnlyCharacter.toRegex()))){
                    errorOnlyCharacterFullname()
                } else {
                    clearFullName()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun textWatcherUserName() {
        binding.registerInputUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    errorNullUserName()
                } else if (!(s.toString().matches(regexMinFullname.toRegex()))) {
                    errorMinUsername()
                }


                else {
                    clearUserName()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun textWatcherEmail() {
        binding.registerInputEmail.addTextChangedListener(object : TextWatcher {
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
        binding.registerInputPassword.addTextChangedListener(object : TextWatcher {
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

    private fun textWatcherConfirmPassword() {
        binding.registerInputConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 1) {
                    errorNullConfirmPassword()
                } else {
                    clearConfirmPassword()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun errorNullFullName(): Boolean {
        binding.registerFullName.error = getText(R.string.error_text_null_fullname)
        errorBorderFullName()
        return false
    }

    private fun errorNullUserName(): Boolean {
        binding.registerUsername.error = getText(R.string.error_text_null_username)
        errorBorderUserName()
        return false
    }

    private fun errorNullEmail(): Boolean {
        binding.registerEmail.error = getText(R.string.error_text_null_email)
        errorBorderEmail()
        return false
    }

    private fun errorNullPassword(): Boolean {
        binding.registerPassword.error = getText(R.string.error_text_null_password)
        errorBorderPassword()
        return false
    }

    private fun errorNullConfirmPassword(): Boolean {
        binding.registerConfirmPassword.error = getText(R.string.error_text_null_confirm_password)
        errorBorderConfirmPassword()
        return false
    }

    private fun errorMinFullname () : Boolean {
        binding.registerFullName.error = getText(R.string.error_text_min_fullname)
        errorBorderFullName()
        return false
    }

    private fun errorMinUsername () : Boolean {
        binding.registerUsername.error = getText(R.string.error_text_min_username)
        errorBorderFullName()
        return false
    }

    private fun errorOnlyCharacterFullname() : Boolean {
        binding.registerFullName.error = getText(R.string.error_text_only_character)
        errorBorderFullName()
        return false
    }

    private fun clearFullName(): Boolean {
        binding.registerFullName.isErrorEnabled = false
        defaultBorderFullName()
        return true
    }

    private fun clearUserName(): Boolean {
        binding.registerUsername.isErrorEnabled = false
        defaultBorderUserName()
        return true
    }

    private fun clearEmail(): Boolean {
        binding.registerEmail.isErrorEnabled = false
        defaultBorderEmail()
        return true
    }

    private fun clearPassword(): Boolean {
        binding.registerPassword.isErrorEnabled = false
        defaultBorderPassword()
        return true
    }

    private fun clearConfirmPassword(): Boolean {
        binding.registerConfirmPassword.isErrorEnabled = false
        defaultBorderConfirmPassword()
        return true
    }

    private fun defaultBorderFullName() {
        binding.registerInputFullName.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderUserName() {
        binding.registerInputUserName.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderEmail() {
        binding.registerInputEmail.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderPassword() {
        binding.registerInputPassword.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun defaultBorderConfirmPassword() {
        binding.registerInputConfirmPassword.setBackgroundResource(R.drawable.slr_outline_button_border)
    }

    private fun errorBorderFullName() {
        binding.registerInputFullName.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderUserName() {
        binding.registerInputUserName.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderEmail() {
        binding.registerInputEmail.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderPassword() {
        binding.registerInputPassword.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    private fun errorBorderConfirmPassword() {
        binding.registerInputConfirmPassword.setBackgroundResource(R.drawable.bg_white_red_outline)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}