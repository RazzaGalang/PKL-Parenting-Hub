package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.model.user.UserData
import com.example.pklparentinghub.utils.EndPoint
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST(EndPoint.LOGIN_ENDPOINT)
    suspend fun postLogin(@Body request: LoginRequest): LoginResponse

    @GET(EndPoint.USER_DATA_ENDPOINT)
    suspend fun getUserData()
    : UserData

}