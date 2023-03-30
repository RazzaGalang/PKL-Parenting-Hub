package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.pklparentinghub.utils.Const
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import okhttp3.MultipartBody

class ApiHelper(private val apiService: ApiService) {
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

    suspend fun requestArticle(request: ArticleRequest) = apiService.postArticle(request)

    suspend fun postImage(filename: MultipartBody.Part) = apiService.postImage(filename)

    suspend fun getArticleDetail(token: String, articleId: Int) = apiService.getArticleDetail(token = token, articleId = articleId)
}