package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("links")
    val links: Links,
    @SerializedName("message")
    val message: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)