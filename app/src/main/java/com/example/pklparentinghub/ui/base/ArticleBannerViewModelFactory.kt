package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ArticleAllRepository
import com.example.pklparentinghub.data.repository.ArticleBannerRepository
import com.example.pklparentinghub.ui.main.viewmodel.ArticleAllViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ArticleBannerViewModel

class ArticleBannerViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleBannerViewModel::class.java)) {
            return ArticleBannerViewModel(ArticleBannerRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}