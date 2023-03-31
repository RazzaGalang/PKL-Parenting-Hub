package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.articleData.ArticleResponse
import com.example.pklparentinghub.data.repository.CreateArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CreateArticleViewModel (private val createArticleRepository: CreateArticleRepository) : ViewModel() {
    private val _articleResult: MutableLiveData<Resource<Response<ArticleResponse>>> = MutableLiveData()
    val articleResult: LiveData<Resource<Response<ArticleResponse>>> get() = _articleResult

    fun requestArticle(title:String, content:String, thumbnail:String) {
        viewModelScope.launch {
            _articleResult.value = Resource.loading(data = null)
            try {
                val request = ArticleRequest(title, content, thumbnail)
                val response = createArticleRepository.requestArticle(request)
                _articleResult.value = Resource.success(response)
            } catch (e: Exception) {
                _articleResult.value = Resource.error(message = "An error occurred", data = null)
            }
        }
    }
}