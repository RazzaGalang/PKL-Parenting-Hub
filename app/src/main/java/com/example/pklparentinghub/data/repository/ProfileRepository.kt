package com.example.pklparentinghub.data.repository

import com.example.pklparentinghub.data.api.ApiHelper

class ProfileRepository(private val apiHelper: ApiHelper) {

    suspend fun getUserData() = apiHelper.getUserData()

}