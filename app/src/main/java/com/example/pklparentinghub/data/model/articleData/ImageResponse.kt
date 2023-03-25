package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("data")
    val `data`: DataImage,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)