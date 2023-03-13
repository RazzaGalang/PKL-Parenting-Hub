package com.example.pklparentinghub.data.remote

import com.example.pklparentinghub.data.api.ApiService
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.utils.Resource
import com.google.gson.Gson

class RemoteDataSource {

//    private val apiService: ApiService
//    suspend fun handleLoginResponse(
//        register:
//    ): Resource<LoginResponse> {
//        val response = apiService.postLogin(
//            register.name,
//            register.username,
//            register.email,
//            register.password
//        )
//
//        // Return Success Result if Response is Successful
//        if (response.isSuccessful) {
//            response.body()?.let { responseResult ->
//                return Resource.success(responseResult)
//            }
//        }
//
//        // Else Return If Error
//        return Resource.error(
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