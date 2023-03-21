package com.example.pklparentinghub.data.model.articleDetail


import com.google.gson.annotations.SerializedName

data class Data(
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
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)