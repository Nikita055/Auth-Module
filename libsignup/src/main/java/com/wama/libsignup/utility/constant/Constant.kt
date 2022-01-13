package com.wama.libsignup.utility.constant

class Constant {
    companion object{
        const val KEY_RESULT = "result"
        const val PACKAGE_LIST="packageList"
        const val ADDONS_LIST="addonsList"
        const val OPEN_CAMERA = 0
        const val OPEN_GALLERY = 1
        const val USER_LOGGED_IN = "user_logged_in"
        const val USER_DETAIL = "userDetails"
        const val DEVICE_ID = "device_id"
        const val USER_ID = "user_id"
        const val USER_TYPE = "user_type"
        const val IS_PHONE_VERIFIED = "isPhoneVerified"
        const val BEARER = "Bearer "
        const val FIREBASE_TOKEN = "deviceToken"
        const val AUTH_TOKEN = "authToken"
        const val IS_NORMAL = "password"
        const val IS_FACEBOOK = "facebook"
        const val IS_GOOGLE = "google"
        const val IS_EMPLOYEE = "employee"
        const val IS_FROM = "iSFrom"
        const val DEVICE_TYPE = "android"
        const val IS_TOKEN_CHANGE = "isTokenChanged"


        //current test base url
        const val BASE_URL="https://dev.wamatechnology.com/projects/washx/public/api/"

        //quickblox
        const val LOGIN_CHAT = "login_chat"
        const val PASSWORD_CHAT = "password_chat"
        const val QUICKBLOX_ID = "quickblox_id"
        const val CHAT_DIALOG_LIST = "chatDialogList"
        const val SHARED_PREFS_NAME = "qb"
        const val QB_USER_ID = "qb_user_id"
        const val QB_USER_LOGIN = "qb_user_login"
        const val QB_USER_PASSWORD = "qb_user_password"
        const val QB_USER_FULL_NAME = "qb_user_full_name"
        const val QB_USER_TAGS = "qb_user_tags"

        const val UNAUTHORIZED = 401

        const val USER_DEFAULT_PASSWORD = "quickblox"
        const val CHAT_PORT = 5223
        const val SOCKET_TIMEOUT = 300
        const val KEEP_ALIVE: Boolean = true
        const val USE_TLS: Boolean = true
        const val AUTO_JOIN: Boolean = false
        const val AUTO_MARK_DELIVERED: Boolean = true
        const val RECONNECTION_ALLOWED: Boolean = true
        const val ALLOW_LISTEN_NETWORK: Boolean = true
    }
}