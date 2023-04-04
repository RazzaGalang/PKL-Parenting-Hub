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

class ArticleEditViewModel (private val articleRepository: ArticleRepository) : ViewModel() {
    private val _editResult: MutableLiveData<Resource<Response<ArticleResponse>>> = MutableLiveData()
    val editResult: LiveData<Resource<Response<ArticleResponse>>> get() = _editResult

    fun editArticle(token: String, articleId: Int, request: ArticleRequest) {
        viewModelScope.launch {
            _editResult.value = Resource.loading(data = null)
            try {
                val response = articleRepository.editArticle(token, articleId, request)
                _editResult.value = Resource.success(response)
            } catch (e: Exception) {
                _editResult.value = Resource.error(message = "An error occurred", data = null)
            }
        }
    }
}