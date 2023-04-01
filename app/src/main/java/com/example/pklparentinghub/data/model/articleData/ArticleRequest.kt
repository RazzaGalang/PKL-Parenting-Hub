package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class ArticleRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
)