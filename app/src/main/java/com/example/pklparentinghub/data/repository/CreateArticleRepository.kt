package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.login.LoginRequest
import com.example.pklparentinghub.data.model.register.RegisterRequest
import okhttp3.MultipartBody

class CreateArticleRepository(private val apiHelper: ApiHelper) {
    suspend fun requestArticle(request: ArticleRequest) = apiHelper.requestArticle(request)

    suspend fun postImage(filename: MultipartBody.Part) = apiHelper.postImage(filename)
}