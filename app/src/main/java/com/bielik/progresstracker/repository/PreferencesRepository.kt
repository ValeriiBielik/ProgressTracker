package com.bielik.progresstracker.repository

import com.bielik.progresstracker.model.UserDetails

interface PreferencesRepository {
    var userDetails: UserDetails?

    fun saveUserName(userName: String)
    fun saveUserImage(image: String)
    fun isUserLoggedIn(): Boolean
}