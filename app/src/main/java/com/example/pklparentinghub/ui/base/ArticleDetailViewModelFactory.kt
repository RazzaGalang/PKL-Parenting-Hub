package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ArticleDetailRepository
import com.example.pklparentinghub.ui.main.viewmodel.ArticleDetailViewModel

class ArticleDetailViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleDetailViewModel::class.java)) {
            return ArticleDetailViewModel(ArticleDetailRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}