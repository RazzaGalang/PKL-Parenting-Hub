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
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @POST(EndPoint.LOGIN_ENDPOINT)
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Headers("Accept: application/json")
    @POST(EndPoint.REGISTER_ENDPOINT)
    suspend fun postRegister(
        @Body request: RegisterRequest
    ) : Response<RegisterResponse>

    @Headers("Accept: application/json")
    @GET("/api/users/{param}")
    fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserDetailResponse>


    //BELOM
    @Headers("Accept: application/json")
    @GET("api/users/{param}/content")
    fun getUserContent(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserContentResponse>

    @Headers("Accept: application/json")
    @GET("/api/users/{param}/followings")
    fun getUserFollowings(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserFollowResponse>

    @Headers("Accept: application/json")
    @GET("/api/users/{param}/follower")
    fun getUserFollower(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserFollowResponse>




}