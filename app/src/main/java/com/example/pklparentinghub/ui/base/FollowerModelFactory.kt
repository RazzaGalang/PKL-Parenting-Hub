package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.ui.main.viewmodel.FollowerViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel

class FollowerModelFactory (private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowerViewModel::class.java)) {
            return FollowerViewModel(ProfileRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}