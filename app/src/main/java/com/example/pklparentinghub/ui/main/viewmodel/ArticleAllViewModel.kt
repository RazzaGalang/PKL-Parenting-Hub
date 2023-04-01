package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class ArticleAllViewModel (private val articleRepository: ArticleRepository) : ViewModel() {

    fun getArticleAll(token: String, perPage: Int, search: String,
    popular: Boolean, latest: Boolean) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = articleRepository.getArticleAll(token, perPage, search, popular, latest)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}