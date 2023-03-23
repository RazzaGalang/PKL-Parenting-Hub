package com.example.pklparentinghub.data.model.articleData


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("from")
    val from: Int,
    @SerializedName("path")
    val path: String,
    @SerializedName("per_page")
    val perPage: String,
    @SerializedName("to")
    val to: Int
)