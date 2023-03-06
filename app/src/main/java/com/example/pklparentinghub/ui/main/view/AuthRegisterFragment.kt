package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentAuthRegisterBinding

class AuthRegisterFragment : Fragment() {

    private var _binding: FragmentAuthRegisterBinding? = null
    private val binding get() = _binding!!

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
        binding.registerFullName.error = getText(R.string.app_name)
        errorBorderFullName()
        return false
    }

    private fun errorNullUserName(): Boolean {
        binding.registerUsername.error = getText(R.string.app_name)
        errorBorderUserName()
        return false
    }

    private fun errorNullEmail(): Boolean {
        binding.registerEmail.error = getText(R.string.app_name)
        errorBorderEmail()
        return false
    }

    private fun errorNullPassword(): Boolean {
        binding.registerPassword.error = getText(R.string.app_name)
        errorBorderPassword()
        return false
    }

    private fun errorNullConfirmPassword(): Boolean {
        binding.registerConfirmPassword.error = getText(R.string.app_name)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}