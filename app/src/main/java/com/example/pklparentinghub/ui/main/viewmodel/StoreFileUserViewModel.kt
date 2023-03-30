package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.userFileUpload.UserFileUploadResponse
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

class StoreFileUserViewModel (private val profileRepository: ProfileRepository): ViewModel() {
    private val _storeUserFileResult: MutableLiveData<Resource<Response<UserFileUploadResponse>>> = MutableLiveData()
    val storeUserFileResult: LiveData<Resource<Response<UserFileUploadResponse>>> get() = _storeUserFileResult

    fun storeUserFile(token : String, image : MultipartBody.Part) {
        viewModelScope.launch {
            _storeUserFileResult.value = Resource.loading(data = null)
            try {
                val response = profileRepository.storeFileUser(token, image)
                _storeUserFileResult.value = Resource.success(response)
            } catch (e: Exception) {
                _storeUserFileResult.value = Resource.error(data = null, message = "An error occurred")
            }
        }
    }
}