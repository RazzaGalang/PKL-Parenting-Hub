package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.register.RegisterResponse
import com.example.pklparentinghub.data.model.userContent.UserContentResponse
import com.example.pklparentinghub.data.model.userDetail.UserDetailResponse
import com.example.pklparentinghub.data.model.userFollower.UserFollowerResponse
import com.example.pklparentinghub.data.model.userFollowing.UserFollowingResponse
import com.example.pklparentinghub.utils.Const
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @POST(Const.Network.LOGIN)
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Headers("Accept: application/json")
    @POST(Const.Network.REGISTER)
    suspend fun postRegister(
        @Body request: RegisterRequest
    ) : Response<RegisterResponse>

    @Headers("Accept: application/json")
    @POST(Const.Network.LOGOUT)
    suspend fun postLogout(
        @Header("Authorization") token: String
    ): Response<JsonObject> //Tambah Model

    @Headers("Accept: application/json")
    @GET(Const.Network.User.USER_ALL)
    suspend fun getProfileUserAll(
        @Header("Authorization") token: String,
        @Query("per_page") perPage : Int,
        @Query("search") search: String
    ): Response<JsonObject>//Tambah Model

    @Headers("Accept: application/json")
    @GET(Const.Network.User.USER_DETAIL)
    suspend fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserDetailResponse>

    //BELOM
    @Headers("Accept: application/json")
    @GET(Const.Network.User.USER_CONTENT)
    suspend fun getUserContent(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserContentResponse>

    @Headers("Accept: application/json")
    @GET(Const.Network.User.USER_FOLLOWING)
    suspend fun getUserFollowings(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserFollowingResponse>

    @Headers("Accept: application/json")
    @GET(Const.Network.User.USER_FOLLOWER)
    suspend fun getUserFollower(
        @Header("Authorization") token: String,
        @Path("param") userId : Int
    ): Response<UserFollowerResponse>

    @Headers("Accept: application/json")
    @GET(Const.Network.Article.ARTICLE_ALL)
    suspend fun getArticleAll(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("search") search: String,
        @Query("popular") popular : Boolean,
        @Query("latest") latest : Boolean
    ) : Response<JsonObject> //Tambah Model

    @Headers("Accept: application/json")
    @GET(Const.Network.Article.ARTICLE_DETAIL)
    suspend fun getArticleDetail(
        @Header("Authorization") token: String,
        @Path ("param") articleId : Int
    ) : Response<JsonObject> //Tambah Model

    @Headers("Accept: application/json")
    @GET(Const.Network.Article.ARTICLE_BANNER)
    suspend fun getArticleBanner(
        @Header("Authorization") token: String,
    ) : Response<JsonObject> //Tambah Model
}