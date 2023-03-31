package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.data.model.articleData.ArticleResponse
import com.example.pklparentinghub.data.model.articleData.ImageResponse
import com.example.pklparentinghub.data.repository.CreateArticleRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

class ImageUploadViewModel (private val createArticleRepository: CreateArticleRepository) : ViewModel() {
    private val _imageResult: MutableLiveData<Resource<Response<ImageResponse>>> = MutableLiveData()
    val imageResult: LiveData<Resource<Response<ImageResponse>>> get() = _imageResult

    fun postImage(filename: MultipartBody.Part) {
        viewModelScope.launch {
            _imageResult.value = Resource.loading(data = null)
            try {
                val response = createArticleRepository.postImage(filename)
                _imageResult.value = Resource.success(response)
            } catch (e: Exception) {
                _imageResult.value = Resource.error(message = "An error occurred", data = null)
            }
        }
    }
}