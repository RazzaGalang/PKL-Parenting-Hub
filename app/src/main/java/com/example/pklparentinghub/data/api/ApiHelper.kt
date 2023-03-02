package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest

class ApiHelper (private val apiService: ApiService) {

    suspend fun requestLogin(request: LoginRequest) = apiService.postLogin(request)
    suspend fun getUserData() = apiService.getUserData()

}