package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.userContent.Article
import com.example.pklparentinghub.data.model.userContent.UserContentResponse
import com.example.pklparentinghub.data.model.userFollower.UserFollowRequest
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import okhttp3.MultipartBody

class ApiHelper (private val apiService: ApiService) {
    suspend fun requestLogin(request: LoginRequest) = apiService.postLogin(request)
    suspend fun requestRegister(request: RegisterRequest) = apiService.postRegister(request)
    suspend fun getUserDetail(token: String, userId: Int) = apiService.getUserDetail(token = token, userId = userId)

    suspend fun storeFileUser(token: String, image: MultipartBody.Part) = apiService.storeFileUser(token = token, image = image)


    suspend fun updateUser(
        token: String,
        userId: Int,
        request: CompleteProfileRequest
    ) = apiService.updateUser(
        token = token,
        userId = userId,
        request = request
    )

    suspend fun getArticleAll(
        token: String,
        perPage: Int,
        search: String,
        popular: Boolean,
        latest: Boolean
    ) = apiService.getArticleAll(
        token = token,
        perPage = perPage,
        search = search,
        popular = popular,
        latest = latest
    )
    suspend fun getUserFollower(token: String, userId: Int) = apiService.getUserFollower(token = token, userId = userId)
    suspend fun getUserFollowings(token: String, userId: Int) = apiService.getUserFollowings(token = token, userId = userId)
    suspend fun postUserFollow(token: String, request: UserFollowRequest) = apiService.postUserFollow(token = token, request )
    suspend fun postUserLike(token: String, articleId: Int) = apiService.postUserLike(token = token, articleId = articleId)
    suspend fun getUserContent(token: String, userId: Int) = apiService.getUserContent(token = token, userId = userId)
}