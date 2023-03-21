package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.repository.AuthRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Resource<Response<LoginResponse>>>()
    val loginResult: LiveData<Resource<Response<LoginResponse>>> = _loginResult


    fun requestLogin(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.loading(data = null)
            try {
                val request = LoginRequest(email, password)
                val response = authRepository.requestLogin(request)
                _loginResult.value = Resource.success(response)
            } catch (e: Exception) {
                _loginResult.value = Resource.error(data = null, message = "An error occurred")
            }
        }
    }
}