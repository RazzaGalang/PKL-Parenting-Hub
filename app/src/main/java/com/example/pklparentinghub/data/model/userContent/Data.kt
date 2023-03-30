package com.example.pklparentinghub.data.model.userContent


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("liked_articles")
    val likedArticles: List<LikedArticle>
)