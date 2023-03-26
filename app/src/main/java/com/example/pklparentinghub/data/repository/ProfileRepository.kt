package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.userContent.Article
import com.example.pklparentinghub.data.model.userFollower.UserFollowRequest

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import okhttp3.MultipartBody

class ProfileRepository (private val apiHelper: ApiHelper) {

    suspend fun requestProfile(token: String, userId: Int) = apiHelper.getUserDetail(token = token, userId = userId)

    suspend fun getUserFollower(token: String, userId: Int) = apiHelper.getUserFollower(token = token, userId = userId)

    suspend fun getUserFollowings(token: String, userId: Int) = apiHelper.getUserFollowings(token = token, userId = userId)

    suspend fun getUserContent(token: String, userId: Int) = apiHelper.getUserContent(token = token, userId = userId)

    suspend fun postUserFollow(token: String, request: UserFollowRequest) = apiHelper.postUserFollow(token = token, request)

    suspend fun postUserLike(token: String, articleId: Int) = apiHelper.postUserLike(token = token, articleId = articleId)
    suspend fun updateUser(token: String, userId: Int, request: CompleteProfileRequest) = apiHelper.updateUser(token = token, userId = userId, request)
    suspend fun storeFileUser(token: String, image : MultipartBody.Part) = apiHelper.storeFileUser(token = token, image = image)

}