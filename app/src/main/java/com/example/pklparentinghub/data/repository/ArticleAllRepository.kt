package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper

class ArticleAllRepository(private val apiHelper: ApiHelper) {

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
}