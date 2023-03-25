package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.CreateArticleRepository
import com.example.pklparentinghub.ui.main.viewmodel.CreateArticleViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ImageUploadViewModel

class ImageUploadViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageUploadViewModel::class.java)) {
            return ImageUploadViewModel(CreateArticleRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}