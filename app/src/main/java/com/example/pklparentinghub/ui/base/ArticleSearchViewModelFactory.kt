package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.ui.main.viewmodel.ArticleSearchViewModel

class ArticleSearchViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleSearchViewModel::class.java)) {
            return ArticleSearchViewModel(ArticleRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}