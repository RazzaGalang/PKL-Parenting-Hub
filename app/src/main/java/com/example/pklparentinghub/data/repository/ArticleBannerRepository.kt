package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper

class ArticleBannerRepository(private val apiHelper: ApiHelper) {

    suspend fun getArticleBanner(token: String) = apiHelper.getArticleBanner(token = token)
}