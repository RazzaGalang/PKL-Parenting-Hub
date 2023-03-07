package com.example.pklparentinghub.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("full_name")
    var fullname: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("password_confirmation")
    var confirmPassword: String
)
