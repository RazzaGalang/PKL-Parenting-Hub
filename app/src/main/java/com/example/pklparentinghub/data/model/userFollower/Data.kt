package com.example.pklparentinghub.data.model.userFollower


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("user")
    val user: List<User>,
    @SerializedName("userCount")
    val userCount: Int
)