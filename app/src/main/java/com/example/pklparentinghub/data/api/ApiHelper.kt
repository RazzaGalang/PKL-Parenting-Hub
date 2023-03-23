package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest

class ApiHelper (private val apiService: ApiService) {
    suspend fun requestLogin(request: LoginRequest) = apiService.postLogin(request)
    suspend fun requestRegister(request: RegisterRequest) = apiService.postRegister(request)
    suspend fun getUserDetail(token: String, userId: Int) = apiService.getUserDetail(token = token, userId = userId)
    suspend fun getUserFollower(token: String, userId: Int) = apiService.getUserFollower(token = token, userId = userId)
    suspend fun getUserFollowings(token: String, userId: Int) = apiService.getUserFollowings(token = token, userId = userId)

    suspend fun getUserContent(token: String, userId: Int) = apiService.getUserContent(token = token, userId = userId)
}