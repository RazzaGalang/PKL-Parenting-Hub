package com.example.pklparentinghub.data.model.methodFollow


import com.google.gson.annotations.SerializedName

data class MethodFollowResponse(
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String
)