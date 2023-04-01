package com.example.pklparentinghub.data.model.userFollower


import com.google.gson.annotations.SerializedName

data class UserFollowRequest(
    @SerializedName("follower_id")
    val followerId: Int
)