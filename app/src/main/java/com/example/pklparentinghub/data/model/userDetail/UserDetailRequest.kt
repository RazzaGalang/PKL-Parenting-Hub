package com.example.pklparentinghub.data.model.userDetail


import com.google.gson.annotations.SerializedName

data class UserDetailRequest(
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("profile_cover")
    val profileCover: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("username")
    val username: String
)