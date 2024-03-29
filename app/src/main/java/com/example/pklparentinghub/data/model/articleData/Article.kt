package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("author")
    val author: Author,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("like")
    val like: Int,
    @SerializedName("is_liked")
    val isLiked: Boolean,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)