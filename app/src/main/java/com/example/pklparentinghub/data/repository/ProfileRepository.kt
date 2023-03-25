package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.model.userFollower.UserFollowRequest

class ProfileRepository (private val apiHelper: ApiHelper) {

    suspend fun requestProfile(token: String, userId: Int) = apiHelper.getUserDetail(token = token, userId = userId)

    suspend fun getUserFollower(token: String, userId: Int) = apiHelper.getUserFollower(token = token, userId = userId)

    suspend fun getUserFollowings(token: String, userId: Int) = apiHelper.getUserFollowings(token = token, userId = userId)

    suspend fun getUserContent(token: String, userId: Int) = apiHelper.getUserContent(token = token, userId = userId)

    suspend fun postUserFollow(token: String, request: UserFollowRequest) = apiHelper.postUserFollow(token = token, request)
}