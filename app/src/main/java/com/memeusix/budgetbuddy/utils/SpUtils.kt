package com.memeusix.budgetbuddy.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.responseModel.AccountListModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryListModel
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.ui.theme.Theme
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpUtils @Inject constructor(val context: Context) {
    val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    var accessToken: String
        get() = getDataByKey(ACCESS_TOKEN)
        set(accessToken) = storeDataByKey(ACCESS_TOKEN, accessToken)

    var isLoggedIn: Boolean
        get() = pref.contains(IS_LOGGED_IN) && pref.getBoolean(IS_LOGGED_IN, true)
        set(isLoggedIn) = pref.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply()

    var themePreference: String
        get() = getDataByKey(THEME_PREFERENCE, Theme.SYSTEM.name)
        set(value) = storeDataByKey(THEME_PREFERENCE, value)

    var user: UserResponseModel?
        get() {
            val gson = Gson()
            val json = getDataByKey(USER_MODEL)
            return gson.fromJson(json, UserResponseModel::class.java)
        }
        set(user) {
            val gson = Gson()
            val json = gson.toJson(user)
            pref.edit().putString(USER_MODEL, json).apply()
        }

    var accountData: AccountListModel?
        get() {
            val gson = Gson()
            val json = getDataByKey(ACCOUNTS)
            return gson.fromJson(json, AccountListModel::class.java)
        }
        set(accountData) {
            val gson = Gson()
            val json = gson.toJson(accountData)
            pref.edit().putString(ACCOUNTS, json).apply()
        }

    var categoriesData: CategoryListModel?
        get() {
            val gson = Gson()
            val json = getDataByKey(CATEGORIES)
            return gson.fromJson(json, CategoryListModel::class.java)
        }
        set(categoriesData) {
            val gson = Gson()
            val json = gson.toJson(categoriesData)
            pref.edit().putString(CATEGORIES, json).apply()
        }

    private fun getDataByKey(key: String, default: String = ""): String {
        return if (pref.contains(key)) {
            pref.getString(key, default).toString()
        } else {
            default
        }
    }

    private fun storeDataByKey(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    // During logout
    fun logout() {
        pref.edit().clear().apply()
    }

    companion object {
        private const val TAG = "SpUtils"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val USER_MODEL = "USER_MODEL"
        private const val ACCOUNTS = "ACCOUNTS"
        private const val CATEGORIES = "CATEGORIES"
        private const val THEME_PREFERENCE = "THEME_PREFERENCE"
    }
}