package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.ApiService
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse

class LoginRepository(private val apiHelper: ApiHelper) {
    suspend fun requestLogin(request: LoginRequest) = apiHelper.requestLogin(request)
}