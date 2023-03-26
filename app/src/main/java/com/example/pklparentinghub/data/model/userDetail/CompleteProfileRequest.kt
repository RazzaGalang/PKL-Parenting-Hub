package com.example.pklparentinghub.data.model.userDetail


import com.google.gson.annotations.SerializedName

data class CompleteProfileRequest(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("profile_cover")
    val profileCover: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("birthday")
    val birthday: String
)