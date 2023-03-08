package com.example.pklparentinghub.data.model.userContent


import com.google.gson.annotations.SerializedName

data class UserContentResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)