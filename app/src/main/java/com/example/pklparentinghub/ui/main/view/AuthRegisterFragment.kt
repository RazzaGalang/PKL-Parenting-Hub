package com.example.pklparentinghub.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.condition.AuthRegisterSuccessFragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.condition.AuthRegisterConnectionErrorFragment
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentAuthRegisterBinding
import com.example.pklparentinghub.ui.base.RegisterViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.RegisterViewModel
import com.example.pklparentinghub.utils.Status

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
    ): View {
        _binding = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextWatcher()
        setupViewModel()
        setupRegister()
        setupObserve()
    }

    private fun initTextWatcher(){
        textWatcherFullName()
        textWatcherUserName()
        textWatcherEmail()
        textWatcherPassword()
        textWatcherConfirmPassword()
    }

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[RegisterViewModel::class.java]
    }

    private fun setupRegister (){
        binding.registerButtonContinue.setOnClickListener {
            if (!errorNullFullName() || !errorNullUserName() || !errorNullEmail() || !errorNullPassword() || !errorNullConfirmPassword()) {
                requestValidateData()
            } else if (binding.registerFullName.isErrorEnabled || binding.registerUsername.isErrorEnabled || binding.registerEmail.isErrorEnabled || binding.registerPassword.isErrorEnabled || binding.registerConfirmPassword.isErrorEnabled){
                requestValidateData()
            } else {
                viewModel.requestRegister(
                    fullname = binding.registerInputFullName.text.toString(),
                    username = binding.registerInputUserName.text.toString(),
                    email = binding.registerInputEmail.text.toString(),
                    password = binding.registerInputPassword.text.toString(),
                    confirmPassword = binding.registerInputConfirmPassword.text.toString())
            }
        }
    }

    private fun requestValidateData() {
        errorNullFullName()
        errorNullUserName()
        errorNullEmail()
        errorNullPassword()
        errorNullConfirmPassword()
    }

    private fun setupObserve() {
        viewModel.registerResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                        val exampleDialog = AuthRegisterSuccessFragment()
                        exampleDialog.show(parentFragmentManager, "example_dialog")
                    }
                    Status.ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(ContentValues.TAG, "setupObservers: " + it.message)
                        val exampleDialog = AuthRegisterConnectionErrorFragment()
                        exampleDialog.show(parentFragmentManager, "example_dialog")
                    }
                    Status.LOADING -> {
                        Log.e(ContentValues.TAG, "setupObservers: LOADING")
                    }
                }
            }
        })
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
                } else if (!(s.toString().matches(regexMinUsername.toRegex()))) {
                    errorMinUsername()
                } else {
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
                } else if (!regexEmailAddress(binding.registerInputEmail.text.toString())) {
                    errorFormatEmail()
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
                } else if (!(s.toString().matches(regexPassword.toRegex()))){
                    errorFormatPassword()
                    errorConfirmPassword()
                } else if (binding.registerInputPassword.text.toString() != binding.registerInputConfirmPassword.text.toString()) {
                    clearPassword()
                    errorConfirmPassword()
                } else if (binding.registerInputPassword.text.toString() == binding.registerInputConfirmPassword.text.toString()) {
                    clearPassword()
                    clearConfirmPassword()
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
                } else if (binding.registerInputPassword.text.toString() != binding.registerInputConfirmPassword.text.toString()) {
                    errorConfirmPassword()
                } else if (binding.registerInputPassword.text.toString() == binding.registerInputConfirmPassword.text.toString()) {
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
        if (binding.registerInputFullName.length() == 0){
            binding.registerFullName.error = getText(R.string.error_text_null_fullname)
            errorBorderFullName()
            return false
        } else {
            return true
        }
    }

    private fun errorNullUserName(): Boolean {
        if (binding.registerInputUserName.length() == 0){
            binding.registerUsername.error = getText(R.string.error_text_null_username)
            errorBorderUserName()
            return false
        } else {
            return true
        }
    }

    private fun errorNullEmail(): Boolean {
        if (binding.registerInputEmail.length() == 0){
            binding.registerEmail.error = getText(R.string.error_text_null_email)
            errorBorderEmail()
            return false
        } else {
            return true
        }
    }

    private fun errorNullPassword(): Boolean {
        if (binding.registerInputPassword.length() == 0){
            binding.registerPassword.error = getText(R.string.error_text_null_password)
            errorBorderPassword()
            return false
        } else {
            return true
        }
    }

    private fun errorNullConfirmPassword(): Boolean {
        if (binding.registerInputConfirmPassword.length() == 0){
            binding.registerConfirmPassword.error = getText(R.string.error_text_null_confirm_password)
            errorBorderConfirmPassword()
            return false
        } else {
            return true
        }
    }

    private fun errorMinFullname () : Boolean {
        binding.registerFullName.error = getText(R.string.error_text_min_fullname)
        errorBorderFullName()
        return false
    }

    private fun errorMinUsername () : Boolean {
        binding.registerUsername.error = getText(R.string.error_text_min_username)
        errorBorderUserName()
        return false
    }

    private fun errorOnlyCharacterFullname() : Boolean {
        binding.registerFullName.error = getText(R.string.error_text_only_character)
        errorBorderFullName()
        return false
    }

    private fun errorFormatEmail () : Boolean {
        binding.registerEmail.error = getText(R.string.error_text_format_email)
        errorBorderEmail()
        return false
    }

    private fun errorFormatPassword () : Boolean {
        binding.registerPassword.error = getText(R.string.error_text_password)
        errorBorderPassword()
        return false
    }

    private fun errorConfirmPassword(): Boolean {
        binding.registerConfirmPassword.error = getText(R.string.error_text_confirm_password)
        errorBorderConfirmPassword()
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

    private fun regexEmailAddress(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target.toString()).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}