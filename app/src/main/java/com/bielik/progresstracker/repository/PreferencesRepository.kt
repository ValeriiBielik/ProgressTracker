package com.bielik.progresstracker.repository

interface PreferencesRepository {
    var userName: String?

    fun isUserLoggedIn(): Boolean
}