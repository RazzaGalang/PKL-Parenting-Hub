package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.register.RegisterResponse
import com.example.pklparentinghub.data.repository.AuthRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel (private val authRepository: AuthRepository) : ViewModel() {
    private val _registerResult: MutableLiveData<Resource<Response<RegisterResponse>>> = MutableLiveData()
    val registerResult: LiveData<Resource<Response<RegisterResponse>>> get() = _registerResult

    fun requestRegister(fullname:String, username:String, email:String, password:String, confirmPassword:String) {
        viewModelScope.launch {
            _registerResult.value = Resource.loading(data = null)
            try {
                val request = RegisterRequest(fullname, username, email, password, confirmPassword)
                val response = authRepository.requestRegister(request)
                _registerResult.value = Resource.success(response)
            } catch (e: Exception) {
                _registerResult.value = Resource.error(message = "An error occurred", data = null)
            }
        }
    }
}