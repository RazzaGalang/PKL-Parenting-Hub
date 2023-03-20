package com.example.pklparentinghub.data.model.register


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("birthday")
    val birthday: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: Any,
    @SerializedName("following")
    val following: Any,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("id")
    val id: Int,
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