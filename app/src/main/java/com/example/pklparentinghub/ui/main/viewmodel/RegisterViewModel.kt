package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.user.User
import com.example.pklparentinghub.data.repository.RegisterRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel (private val registerRepository: RegisterRepository) : ViewModel() {
    private val _registerResult: MutableLiveData<Resource<User>> = MutableLiveData()
    val registerResult: LiveData<Resource<User>> get() = _registerResult

    fun requestRegister(fullname:String, username:String, email:String, password:String, confirmPassword:String) {
        viewModelScope.launch {
            _registerResult.value = Resource.loading(data = null)
            try {
                val request = RegisterRequest(fullname, username, email, password, confirmPassword)
                val response = registerRepository.requestRegister(request)
                _registerResult.value = Resource.success(response)
            } catch (e: Exception) {
                _registerResult.value = Resource.error(data = null, message = "An error occurred")
            }
        }
    }

}