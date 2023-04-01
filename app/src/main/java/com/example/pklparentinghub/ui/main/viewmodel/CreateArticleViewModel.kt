package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.articleData.ArticleResponse
import com.example.pklparentinghub.data.repository.ArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CreateArticleViewModel (private val articleRepository: ArticleRepository) : ViewModel() {
    private val _articleResult: MutableLiveData<Resource<Response<ArticleResponse>>> = MutableLiveData()
    val articleResult: LiveData<Resource<Response<ArticleResponse>>> get() = _articleResult

    fun requestArticle(token: String, request: ArticleRequest) {
        viewModelScope.launch {
            _articleResult.value = Resource.loading(data = null)
            try {
                val response = articleRepository.requestArticle(token, request)
                _articleResult.value = Resource.success(response)
            } catch (e: Exception) {
                _articleResult.value = Resource.error(message = "An error occurred", data = null)
            }
        }
    }
}