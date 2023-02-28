package com.example.pklparentinghub.data.model.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("article")
    val article: List<Article>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("follower")
    val follower: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("profile_cover")
    val profileCover: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)