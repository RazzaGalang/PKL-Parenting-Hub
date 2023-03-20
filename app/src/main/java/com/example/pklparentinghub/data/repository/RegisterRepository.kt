package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.register.RegisterRequest

class RegisterRepository (private val apiHelper: ApiHelper) {
    suspend fun requestRegister(request: RegisterRequest) = apiHelper.requestRegister(request)
}