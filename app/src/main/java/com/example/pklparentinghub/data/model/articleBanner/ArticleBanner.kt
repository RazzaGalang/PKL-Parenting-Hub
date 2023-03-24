package com.example.pklparentinghub.data.model.articleBanner


import com.google.gson.annotations.SerializedName

data class ArticleBanner(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)