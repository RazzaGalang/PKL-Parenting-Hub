package com.example.pklparentinghub.data.model.userContent


import com.google.gson.annotations.SerializedName

data class LikedArticle(
    @SerializedName("author")
    val author: Author,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("likes_count")
    val likesCount: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)