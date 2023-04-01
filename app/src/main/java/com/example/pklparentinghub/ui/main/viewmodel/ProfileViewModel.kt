package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class ProfileViewModel (private val profileRepository: ProfileRepository): ViewModel() {

    fun requestProfile(token : String, userId : Int ) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            emit(Resource.success(data = profileRepository.requestProfile(token, userId )))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred"))
        }
    }
}