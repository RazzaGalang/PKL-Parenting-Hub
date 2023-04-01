package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class ContentViewModel (private val profileRepository: ProfileRepository): ViewModel() {
    fun getUserContent(token: String, userId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try{
            emit(Resource.success(data = profileRepository.getUserContent(token, userId )))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred"))
        }
    }
}