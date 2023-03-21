package com.example.pklparentinghub.data.source.remote

import com.example.pklparentinghub.data.api.ApiService
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.utils.Resource
import com.google.gson.Gson

class AuthRemoteDataSource (private val apiService: ApiService) {

//    suspend fun handleLoginResponse(
//        request: LoginRequest
//    ): Resource<LoginResponse> {
//        val response = apiService.postLogin(
//            request
//        )
//
//        if (response.isSuccessful) {
//            response.body()?.let { responseResult ->
//                return Resource.success(responseResult)
//            }
//        }
//
//        // Else Return If Error
//        return Resource.error()(
//            // Use Gson Parser If the errors was List
//            message = Gson().toJson(
//                response.errorBody()
//                    ?.parse<BaseResponse<List<String>>>()
//                    ?.errors
//                        // Else Use Message String Instead
//                        message = response.errorBody()
//                    ?.parse<AuthResponse>()
//                    ?.message
//            )
//        )
//    }
}