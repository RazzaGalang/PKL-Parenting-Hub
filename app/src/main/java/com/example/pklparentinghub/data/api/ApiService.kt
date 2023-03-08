package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.register.RegisterResponse
import com.example.pklparentinghub.data.model.userContent.UserContentResponse
import com.example.pklparentinghub.data.model.userDetail.UserDetailResponse
import com.example.pklparentinghub.data.model.userFollow.UserFollowResponse
import com.example.pklparentinghub.utils.EndPoint
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @POST(EndPoint.LOGIN_ENDPOINT)
    suspend fun postLogin(@Body request: LoginRequest): LoginResponse

    @Headers("Accept: application/json")
    @POST(EndPoint.REGISTER_ENDPOINT)
    suspend fun postRegister(@Body request: RegisterRequest) : RegisterResponse

    @Headers("Accept: application/json")
    @GET("/api/users/{id}")
    fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("id") userId : Int
    ): Call<UserDetailResponse>

    @Headers("Accept: application/json")
    @GET("api/users/{}/content")
    fun getUserContent(@Header("Authorization") token: String): Call<UserContentResponse>

    @Headers("Accept: application/json")
    @GET("/api/users/{}/followings")
    fun getUserFollowings(@Header("Authorization") token: String): Call<UserFollowResponse>

    @Headers("Accept: application/json")
    @GET("/api/users/{}/follower")
    fun getUserFollower(@Header("Authorization") token: String): Call<UserFollowResponse>




}