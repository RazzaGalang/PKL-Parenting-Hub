package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: Any,
    @SerializedName("next")
    val next: String,
    @SerializedName("prev")
    val prev: Any
)