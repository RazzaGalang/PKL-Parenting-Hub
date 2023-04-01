package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.ui.main.viewmodel.CreateArticleViewModel

class CreateArticleViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateArticleViewModel::class.java)) {
            return CreateArticleViewModel(ArticleRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}