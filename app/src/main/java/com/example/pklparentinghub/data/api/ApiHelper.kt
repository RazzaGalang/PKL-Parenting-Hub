package com.example.pklparentinghub.data.api

import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.pklparentinghub.utils.Const
import okhttp3.MultipartBody

class ApiHelper(private val apiService: ApiService) {
    suspend fun requestLogin(request: LoginRequest) = apiService.postLogin(request)
    suspend fun requestRegister(request: RegisterRequest) = apiService.postRegister(request)

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