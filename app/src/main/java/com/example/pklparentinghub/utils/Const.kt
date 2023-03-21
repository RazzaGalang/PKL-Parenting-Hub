package com.example.pklparentinghub.utils

object Const {
    const val CONST_BASE_URL = "https://42c7-125-164-17-166.ap.ngrok.io/"

    const val PREFIX = "api/"
    object Network {
        const val LOGIN = PREFIX +  "login"
        const val REGISTER = PREFIX + "register"
        const val USER = PREFIX + "users/{param}"
        const val USER_CONTENT = PREFIX + "users/{param}/content"
        const val USER_FOLLOWER = PREFIX + "users/{param}/follower"
        const val USER_FOLLOWING = PREFIX + "users/{param}/following"
    }

    object Token {
        const val AUTH_PREFIX = "Bearer"
    }
}

