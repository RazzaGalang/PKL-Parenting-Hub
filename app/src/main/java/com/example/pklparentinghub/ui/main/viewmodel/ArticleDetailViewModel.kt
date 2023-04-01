package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class ArticleDetailViewModel (private val articleRepository: ArticleRepository) : ViewModel() {

    fun getArticleDetail(token: String, articleId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = articleRepository.getArticleDetail(token, articleId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}