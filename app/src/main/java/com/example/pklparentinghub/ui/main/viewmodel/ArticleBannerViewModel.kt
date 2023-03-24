package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.repository.ArticleAllRepository
import com.example.pklparentinghub.data.repository.ArticleBannerRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class ArticleBannerViewModel (private val articleBannerRepository: ArticleBannerRepository) : ViewModel() {

    fun getArticleBanner(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = articleBannerRepository.getArticleBanner(token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}