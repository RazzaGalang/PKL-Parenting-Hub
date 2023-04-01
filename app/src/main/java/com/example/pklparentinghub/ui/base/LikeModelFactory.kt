package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.ui.main.viewmodel.LikeViewModel

class LikeModelFactory (private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikeViewModel::class.java)) {
            return LikeViewModel(ProfileRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}