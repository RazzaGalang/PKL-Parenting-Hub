package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.pklparentinghub.utils.Const
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import com.example.pklparentinghub.data.model.userFollower.UserFollowRequest
import okhttp3.MultipartBody

class ApiHelper (private val apiService: ApiService) {
    suspend fun requestLogin(request: LoginRequest) = apiService.postLogin(request)

    suspend fun requestRegister(request: RegisterRequest) = apiService.postRegister(request)

    suspend fun requestLogout(token: String) = apiService.postLogout(token = token)

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

    suspend fun requestArticle(token: String, request: ArticleRequest) = apiService.postArticle(token, request)

    suspend fun postImage(token: String, image: MultipartBody.Part) = apiService.postImage(token = token, image = image)

    suspend fun getArticleDetail(token: String, articleId: Int) = apiService.getArticleDetail(token = token, articleId = articleId)

    suspend fun getArticleSearch(
        token: String,
        search: String
    ) = apiService.getArticleSearch(
        token = token,
        search = search
    )
}