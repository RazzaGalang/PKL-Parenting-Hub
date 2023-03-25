package com.example.pklparentinghub.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pklparentinghub.data.model.register.RegisterRequest
import com.example.pklparentinghub.data.model.userFollower.UserFollowRequest
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.utils.Resource
import kotlinx.coroutines.Dispatchers

class FollowViewModel (private val profileRepository: ProfileRepository) : ViewModel() {

    fun postUserFollow (token : String, followerId : Int ) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try{
            val request = UserFollowRequest(followerId)
            emit(Resource.success(data = profileRepository.postUserFollow(token, request )))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred"))
        }
    }
}