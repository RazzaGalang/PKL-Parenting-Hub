package com.example.pklparentinghub.data.api

class ApiHelper (private val apiService: ApiService) {

    suspend fun getUserData() = apiService.getUserData()

}