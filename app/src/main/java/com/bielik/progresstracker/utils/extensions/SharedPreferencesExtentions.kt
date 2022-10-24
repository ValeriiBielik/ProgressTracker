package com.bielik.progresstracker.utils.extensions

import android.content.SharedPreferences
import androidx.core.content.edit
import com.bielik.progresstracker.utils.convertToString
import com.bielik.progresstracker.utils.toObject
import com.google.gson.Gson

inline fun <reified T> SharedPreferences.getJsonObject(gson: Gson, prefKey: String): T? =
    getString(prefKey, null)
        ?.toObject<T>(gson)

fun <T> SharedPreferences.putAsJsonObject(gson: Gson, prefKey: String, data: T) {
    edit(commit = true) {
        putString(prefKey, data.convertToString(gson))
    }
}
