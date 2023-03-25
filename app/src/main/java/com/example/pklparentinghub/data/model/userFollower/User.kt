package com.example.pklparentinghub.data.model.userFollower


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: Any,
    @SerializedName("following")
    val following: Any,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_followings")
    val isFollowings: Boolean,
    @SerializedName("profile_cover")
    val profileCover: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("verifikasi")
    val verifikasi: Boolean
)