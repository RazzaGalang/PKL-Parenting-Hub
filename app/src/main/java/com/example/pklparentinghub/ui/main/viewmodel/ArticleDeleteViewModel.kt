package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.articleData.ArticleResponse
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ArticleDeleteViewModel (private val articleRepository: ArticleRepository) : ViewModel() {
    private val _deleteResult: MutableLiveData<Resource<Response<ArticleResponse>>> = MutableLiveData()
    val deleteResult: LiveData<Resource<Response<ArticleResponse>>> get() = _deleteResult

    fun deleteArticle(token: String, articleId: Int) {
        viewModelScope.launch {
            _deleteResult.value = Resource.loading(data = null)
            try {
                val response = articleRepository.deleteArticle(token, articleId)
                _deleteResult.value = Resource.success(response)
            } catch (e: Exception) {
                _deleteResult.value = Resource.error(message = "An error occurred", data = null)
            }
        }
    }
}