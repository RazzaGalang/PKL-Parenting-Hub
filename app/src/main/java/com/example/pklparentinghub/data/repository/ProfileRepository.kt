package com.example.pklparentinghub.data.repository


import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import okhttp3.MultipartBody

class ProfileRepository (private val apiHelper: ApiHelper) {

    suspend fun requestProfile(token: String, userId: Int) = apiHelper.getUserDetail(token = token, userId = userId)
    suspend fun updateUser(token: String, userId: Int, request: CompleteProfileRequest) = apiHelper.updateUser(token = token, userId = userId, request)
    suspend fun storeFileUser(token: String, image : MultipartBody.Part) = apiHelper.storeFileUser(token = token, image = image)

}