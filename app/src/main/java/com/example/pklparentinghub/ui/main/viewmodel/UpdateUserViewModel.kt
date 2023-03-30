package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import com.example.pklparentinghub.data.model.userDetail.UserDetailResponse
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateUserViewModel (private val updateUserRepository: ProfileRepository) : ViewModel() {
    private val _updateUserResult: MutableLiveData<Resource<Response<UserDetailResponse>>> = MutableLiveData()
    val updateUserResult: LiveData<Resource<Response<UserDetailResponse>>> get() = _updateUserResult

    fun updateUser(token : String, userId : Int, request: CompleteProfileRequest ) {
        viewModelScope.launch {
            _updateUserResult.value = Resource.loading(data = null)
            try {
                val response = updateUserRepository.updateUser(token, userId, request = request )
                _updateUserResult.value = Resource.success(response)
            } catch (e: Exception) {
                _updateUserResult.value = Resource.error(data = null, message = "An error occurred")
            }
        }
    }
}