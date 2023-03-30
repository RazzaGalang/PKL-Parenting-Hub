package com.example.pklparentinghub.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.repository.ProfileRepository
import com.example.pklparentinghub.ui.main.viewmodel.StoreFileUserViewModel

class StoreFileUserViewModelFactory (private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreFileUserViewModel::class.java)) {
            return StoreFileUserViewModel(ProfileRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}