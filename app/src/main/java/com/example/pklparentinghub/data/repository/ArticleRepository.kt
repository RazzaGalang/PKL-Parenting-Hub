package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import okhttp3.MultipartBody

class ArticleRepository(private val apiHelper: ApiHelper) {

    suspend fun getArticleAll(
        token: String,
        perPage: Int,
        search: String,
        popular: Boolean,
        latest: Boolean
    ) = apiHelper.getArticleAll(
        token = token,
        perPage = perPage,
        search = search,
        popular = popular,
        latest = latest
    )

    suspend fun getArticleSearch(
        token: String,
        search: String
    ) = apiHelper.getArticleSearch(
        token = token,
        search = search
    )

    suspend fun getArticleDetail(token: String, articleId: Int) =
        apiHelper.getArticleDetail(token = token, articleId = articleId)

    suspend fun requestArticle(token: String, request: ArticleRequest) = apiHelper.requestArticle(token, request)

    suspend fun postImage(token: String, image : MultipartBody.Part) =
        apiHelper.postImage(token = token, image = image)
}