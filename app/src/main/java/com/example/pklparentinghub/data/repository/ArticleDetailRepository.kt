package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper

class ArticleDetailRepository(private val apiHelper: ApiHelper) {

    suspend fun getArticleDetail(token: String, articleId: Int) = apiHelper.getArticleDetail(token = token, articleId = articleId)
}