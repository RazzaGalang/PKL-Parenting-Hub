package com.example.pklparentinghub.data.model.methodFollow


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("follower_id")
    val followerId: List<String>,
    @SerializedName("user_id")
    val userId: List<String>
)