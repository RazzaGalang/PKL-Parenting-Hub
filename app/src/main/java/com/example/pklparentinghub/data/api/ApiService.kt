package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.register.RegisterResponse
import com.example.pklparentinghub.utils.EndPoint
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST(EndPoint.LOGIN_ENDPOINT)
    suspend fun postLogin(@Body request: LoginRequest): LoginResponse

    @Headers("Accept: application/json")
    @POST(EndPoint.REGISTER_ENDPOINT)
    suspend fun postRegister(@Body request: RegisterRequest) : RegisterResponse

}