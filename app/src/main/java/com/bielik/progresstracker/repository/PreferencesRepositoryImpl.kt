package com.bielik.progresstracker.repository

import android.content.SharedPreferences
import com.bielik.progresstracker.model.UserDetails
import com.bielik.progresstracker.utils.extensions.getJsonObject
import com.bielik.progresstracker.utils.extensions.putAsJsonObject
import com.google.gson.Gson

private const val KEY_USER_DETAILS: String = "key_user_details"

class PreferencesRepositoryImpl(private val preferences: SharedPreferences) : PreferencesRepository {

    override var userDetails: UserDetails?
        get() = preferences.getJsonObject(Gson(), KEY_USER_DETAILS)
        set(value) {
            preferences.putAsJsonObject(Gson(), KEY_USER_DETAILS, value)
        }

    override fun saveUserName(userName: String) {
        val user = userDetails
        if (user == null) {
            userDetails = UserDetails().apply { this.userName = userName }
        } else {
            userDetails = user.copy(userName = userName)
        }
    }

    override fun saveUserImage(image: String) {
        val user = userDetails
        if (user == null) {
            userDetails = UserDetails().apply { this.userImage = image }
        } else {
            userDetails = user.copy(userImage = image)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return !userDetails?.userName.isNullOrBlank()
    }
}