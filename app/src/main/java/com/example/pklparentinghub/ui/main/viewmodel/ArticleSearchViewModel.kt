package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class ArticleSearchViewModel (private val articleRepository: ArticleRepository) : ViewModel() {

    fun getArticleSearch(token: String, search: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = articleRepository.getArticleSearch(token, search)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}