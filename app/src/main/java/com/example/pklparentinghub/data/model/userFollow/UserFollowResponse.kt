package com.example.pklparentinghub.data.model.userFollow


import com.google.gson.annotations.SerializedName

data class UserFollowResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)