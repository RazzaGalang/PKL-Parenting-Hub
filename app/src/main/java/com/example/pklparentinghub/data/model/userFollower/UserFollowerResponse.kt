package com.example.pklparentinghub.data.model.userFollower


import com.google.gson.annotations.SerializedName

data class UserFollowerResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)