package com.bielik.progresstracker.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_USER_NAME: String = "key_user_name"

class PreferencesRepositoryImpl(private val preferences: SharedPreferences) : PreferencesRepository {

    override var userName: String
        get() = preferences.getString(KEY_USER_NAME, "") ?: ""
        set(value) {
            preferences.edit { putString(KEY_USER_NAME, value) }
        }

    override fun isUserLoggedIn(): Boolean {
        return !preferences.getString(KEY_USER_NAME, "").isNullOrBlank()
    }
}