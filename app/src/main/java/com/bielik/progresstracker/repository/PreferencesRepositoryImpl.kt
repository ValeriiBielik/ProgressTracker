package com.bielik.progresstracker.repository

import android.content.SharedPreferences

private const val KEY_USER_NAME: String = "key_user_name"

class PreferencesRepositoryImpl(private val preferences: SharedPreferences) : PreferencesRepository {

    override var userName: String?
        get() = preferences.getString(KEY_USER_NAME, "")
        set(value) {
            preferences.edit().putString(KEY_USER_NAME, value).apply()
        }

    override fun isUserLoggedIn(): Boolean {
        return !userName.isNullOrBlank()
    }
}