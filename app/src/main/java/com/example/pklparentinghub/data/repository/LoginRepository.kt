package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.login.LoginRequest

class LoginRepository(private val apiHelper: ApiHelper) {
    suspend fun requestLogin(request: LoginRequest) = apiHelper.requestLogin(request)
}