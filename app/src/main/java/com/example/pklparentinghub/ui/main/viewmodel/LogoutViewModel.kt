package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.repository.AuthRepository
import com.example.pklparentinghub.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class LogoutViewModel (private val authRepository: AuthRepository) : ViewModel() {
    private val _logoutResult: MutableLiveData<Resource<Response<JsonObject>>> = MutableLiveData()
    val logoutResult: LiveData<Resource<Response<JsonObject>>> get() = _logoutResult

    fun requestLogout(token : String) {
        viewModelScope.launch {
            _logoutResult.value = Resource.loading(data = null)
            try {
                val response = authRepository.requestLogut(token)
                _logoutResult.value = Resource.success(response)
            } catch (e: Exception) {
                _logoutResult.value = Resource.error(data = null, message = "An error occurred")
            }
        }
    }
}