package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.articleData.ArticleResponse
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.articleData.ImageResponse
import com.example.pklparentinghub.data.model.articleDetail.ArticleDetail
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.login.LoginResponse
import com.example.pklparentinghub.data.model.methodFollow.MethodFollowResponse
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.register.RegisterResponse
import com.example.pklparentinghub.data.model.userContent.UserContentResponse
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import com.example.pklparentinghub.data.model.userDetail.UserDetailResponse
import com.example.pklparentinghub.data.model.userFileUpload.UserFileUploadResponse
import com.example.pklparentinghub.data.model.userFollower.UserFollowRequest
import com.example.pklparentinghub.data.model.userFollower.UserFollowerResponse
import com.example.pklparentinghub.data.model.userFollowing.UserFollowingResponse
import com.example.pklparentinghub.utils.Const
import com.google.gson.JsonObject
import okhttp3.MultipartBody
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
    ): Response<JsonObject>

    @Headers("Accept: application/json")
    @PUT(Const.Network.User.USER_EDIT)
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("param") userId : Int,
        @Body request: CompleteProfileRequest
    ): Response<UserDetailResponse>

    @Multipart
    @Headers("Accept: application/json")
    @POST(Const.Network.User.USER_UPLOAD_FILE)
    suspend fun storeFileUser(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<UserFileUploadResponse>


    @Headers("Accept: application/json")
    @GET(Const.Network.User.USER_ALL)
    suspend fun getProfileUserAll(
        @Header("Authorization") token: String,
        @Query("per_page") perPage : Int,
        @Query("search") search: String
    ): Response<JsonObject>

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
    @POST(Const.Network.User.USER_FOLLOW)
    suspend fun postUserFollow(
        @Header("Authorization") token: String,
        @Body request: UserFollowRequest
    ): Response<MethodFollowResponse>

    @Headers("Accept: application/json")
    @POST(Const.Network.Article.USER_LIKE)
    suspend fun postUserLike(
        @Header("Authorization") token: String,
        @Path("param") articleId: Int
    ): Response<UserContentResponse>

    @Headers("Accept: application/json")
    @GET(Const.Network.Article.ARTICLE_ALL)
    suspend fun getArticleAll(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("search") search: String,
        @Query("popular") popular : Boolean,
        @Query("latest") latest : Boolean
    ) : ArticleResponse

    @Headers("Accept: application/json")
    @GET(Const.Network.Article.ARTICLE_DETAIL)
    suspend fun getArticleDetail(
        @Header("Authorization") token: String,
        @Path ("param") articleId : Int
    ) : ArticleDetail

    @Headers("Accept: application/json")
    @POST(Const.Network.Article.ARTICLE_POST)
    suspend fun postArticle(
        @Header("Authorization") token: String,
        @Body request: ArticleRequest
    ): Response<ArticleResponse>

    @Multipart
    @Headers("Accept: application/json")
    @POST(Const.Network.Article.IMAGE_UPLOAD)
    suspend fun postImage(
        @Header("Authorization") token: String,
        @Part image : MultipartBody.Part
    ): Response<ImageResponse>

    @Headers("Accept: application/json")
    @GET(Const.Network.Article.ARTICLE_ALL)
    suspend fun getArticleSearch(
        @Header("Authorization") token: String,
        @Query("search") search: String
    ) : ArticleResponse
}