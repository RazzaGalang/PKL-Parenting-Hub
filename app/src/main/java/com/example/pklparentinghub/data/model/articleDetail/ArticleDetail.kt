package com.example.pklparentinghub.data.model.articleDetail


import com.google.gson.annotations.SerializedName

data class ArticleDetail(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)