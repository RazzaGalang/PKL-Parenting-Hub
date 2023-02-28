package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiService
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.utils.Resource
import com.google.gson.JsonElement

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }
}