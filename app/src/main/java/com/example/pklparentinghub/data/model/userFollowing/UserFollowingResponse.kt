package com.example.pklparentinghub.data.model.userFollowing


import com.google.gson.annotations.SerializedName

data class UserFollowingResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)