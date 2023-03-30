package com.example.pklparentinghub.utils

object Const {
    const val CONST_BASE_URL = "https://5646-114-122-74-17.ap.ngrok.io"

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
            const val USER_FOLLOWER = PREFIX + "users/{param}/follower"
            const val USER_FOLLOWING = PREFIX + "users/{param}/following"
            const val USER_EDIT = PREFIX + "users/{param}"
            const val USER_UPLOAD_FILE = PREFIX + "users/profile/upload"
        }

        object Article {
            const val ARTICLE_ALL = PREFIX + "articles"
            const val ARTICLE_DETAIL = PREFIX + "articles/{param}"
            const val ARTICLE_BANNER = PREFIX + "articles/banner"
            const val ARTICLE_POST = PREFIX + "articles"
            const val IMAGE_UPLOAD = PREFIX + "articles/upload"
        }
    }
}

