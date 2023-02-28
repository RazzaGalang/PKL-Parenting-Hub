package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.repository.LoginRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Resource<LoginResponse>>()
    val loginResult: LiveData<Resource<LoginResponse>> = _loginResult
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.loading(data = null)
            try {
                val response = loginRepository.login(email, password)
                _loginResult.value = Resource.success(response)
            } catch (e: Exception) {
                _loginResult.value = Resource.error(data = null, message = "Error Occurred!")
            }
        }
    }
}