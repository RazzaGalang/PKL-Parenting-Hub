package com.example.pklparentinghub.utils

object Const {
    const val CONST_BASE_URL = "https://d3c3-114-122-102-207.ap.ngrok.io"

    const val PREFIX = "api/"

    object Token {
        const val AUTH_PREFIX = "Bearer"
    }
    object Network {
        const val LOGIN = PREFIX +  "login"
        const val REGISTER = PREFIX + "register"
        const val LOGOUT = PREFIX + "logout"

        object User {
            const val USER_ALL = PREFIX + "users"
            const val USER_DETAIL = PREFIX + "users/{param}"
            const val USER_CONTENT = PREFIX + "users/{param}/content"
            const val USER_FOLLOWER = PREFIX + "users/{param}/followers"
            const val USER_FOLLOWING = PREFIX + "users/{param}/followings"
            const val USER_FOLLOW = PREFIX + "users/follow"
            const val USER_EDIT = PREFIX + "users/{param}"
            const val USER_UPLOAD_FILE = PREFIX + "users/profile/upload"
        }

        object Article {
            const val USER_LIKE = PREFIX + "articles/{param}/like"
            const val ARTICLE_ALL = PREFIX + "articles"
            const val ARTICLE_DETAIL = PREFIX + "articles/{param}"
            const val ARTICLE_BANNER = PREFIX + "articles/banner"
            const val ARTICLE_POST = PREFIX + "articles"
            const val IMAGE_UPLOAD = PREFIX + "articles/upload"
        }
    }
}

