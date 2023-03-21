package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest

class AuthRepository(private val apiHelper: ApiHelper) {
    suspend fun requestLogin(request: LoginRequest) = apiHelper.requestLogin(request)

    suspend fun requestRegister(request: RegisterRequest) = apiHelper.requestRegister(request)
}