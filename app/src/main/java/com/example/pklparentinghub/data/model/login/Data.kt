package com.example.pklparentinghub.data.model.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)