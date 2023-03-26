package com.example.pklparentinghub.data.model.userFileUpload


import com.google.gson.annotations.SerializedName

data class UserFileUploadResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)