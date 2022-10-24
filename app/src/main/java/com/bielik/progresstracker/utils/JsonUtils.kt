package com.bielik.progresstracker.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONTokener
import java.lang.reflect.Type

fun <T> T.convertToString(gson: Gson): String = gson.toJson(this)

val unitType: Type = object : TypeToken<Unit>() {}.type

inline fun <reified T> String.toObject(gson: Gson): T? {
    val type = object : TypeToken<T>() {}.type
    return try {
        val isJsonAnArray = kotlin.runCatching { JSONTokener(this).nextValue() is JSONArray }.getOrElse { false }

        val isObjectArray = T::class.java.isArray
        val isObjectCollection = Collection::class.java.isAssignableFrom(T::class.java)
        val isArrayJsonRepresentableByObject = isObjectArray || isObjectCollection
        val typesNotMatch = isJsonAnArray && !isArrayJsonRepresentableByObject

        when {
            type == unitType -> Unit as T
            typesNotMatch -> null
            else -> gson.fromJson(this, type)
        }
    } catch (t: Throwable) {
        Log.e("toObject", t.localizedMessage, t)
        null
    }
}
