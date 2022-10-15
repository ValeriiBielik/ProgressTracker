package com.bielik.progresstracker.common

import android.content.Context
import android.util.Log
import androidx.annotation.PluralsRes

open class StringResource(val resId: Int, vararg val formatArgs: Any) {
    open fun getString(context: Context) = try {
        context.getString(resId, *formatArgs)
    } catch (throwable: Throwable) {
        Log.e(StringResource::class.simpleName, "Error:", throwable)
        ""
    }
}

class NonTranslatableStringResource(val nonTranslatableString: String?) : StringResource(0) {
    override fun getString(context: Context): String = nonTranslatableString ?: ""
}

class PluralStringResource(@PluralsRes resId: Int, val quantity: Int, vararg formatArgs: Any) :
    StringResource(resId, *formatArgs) {
    override fun getString(context: Context): String =
        context.resources.getQuantityString(resId, quantity, *formatArgs)
}
