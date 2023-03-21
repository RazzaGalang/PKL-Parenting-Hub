package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("article")
    val article: List<Article>,
    @SerializedName("articleCount")
    val articleCount: Int
)