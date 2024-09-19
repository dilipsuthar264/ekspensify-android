package com.memeusix.budgetbuddy.utils

import android.content.Context
import android.content.SharedPreferences
import com.memeusix.budgetbuddy.R

class SpUtils(private val context: Context) {
    val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    var accessToken: String
        get() = getDataByKey(ACCESS_TOKEN)
        set(accessToken) = storeDataByKey(ACCESS_TOKEN, accessToken)

    var isLoggedIn: Boolean
        get() = pref.contains(IS_LOGGED_IN) && pref.getBoolean(IS_LOGGED_IN, true)
        set(isLoggedIn) = pref.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply()


    private fun getDataByKey(key: String): String {
        return if (pref.contains(key)) {
            pref.getString(key, "").toString()
        } else {
            ""
        }
    }

    private fun storeDataByKey(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    companion object {
        private const val TAG = "SpUtils"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"

    }
}